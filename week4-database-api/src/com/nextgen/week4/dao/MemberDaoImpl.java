package com.nextgen.week4.dao;

import com.nextgen.week4.config.DatabaseConfig;
import com.nextgen.week4.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Robust DAO implementation for Members.
 */
public class MemberDaoImpl implements MemberDao {
    private final Map<Integer, Member> memoryStore = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(10);

    public MemberDaoImpl() {
        seedInitialMembers();
    }

    private void seedInitialMembers() {
        saveInMemory(new Member(1, "Aditya Rao", "aditya.rao@nextgenhub.in", "9876543210", "2026-01-15"));
        saveInMemory(new Member(2, "Kavita Sen", "kavita.sen@nextgenhub.in", "9876543211", "2026-02-01"));
        saveInMemory(new Member(3, "Manish Kumar", "manish.k@nextgenhub.in", "9876543212", "2026-02-10"));
    }

    private void saveInMemory(Member m) {
        memoryStore.put(m.getId(), m);
    }

    @Override
    public Member save(Member member) {
        if (DatabaseConfig.testMySQLConnection()) {
            String sql = "INSERT INTO members (name, email, phone, membership_date) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseConfig.getMySQLConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, member.getName());
                stmt.setString(2, member.getEmail());
                stmt.setString(3, member.getPhone());
                stmt.setString(4, member.getMembershipDate());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        member.setId(rs.getInt(1));
                    }
                }
                memoryStore.put(member.getId(), member);
                return member;
            } catch (Exception ignored) {}
        }

        if (member.getId() <= 0) {
            member.setId(idSequence.incrementAndGet());
        }
        memoryStore.put(member.getId(), member);
        return member;
    }

    @Override
    public boolean update(Member member) {
        if (DatabaseConfig.testMySQLConnection()) {
            String sql = "UPDATE members SET name=?, email=?, phone=?, membership_date=? WHERE id=?";
            try (Connection conn = DatabaseConfig.getMySQLConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, member.getName());
                stmt.setString(2, member.getEmail());
                stmt.setString(3, member.getPhone());
                stmt.setString(4, member.getMembershipDate());
                stmt.setInt(5, member.getId());
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    memoryStore.put(member.getId(), member);
                    return true;
                }
            } catch (Exception ignored) {}
        }
        if (memoryStore.containsKey(member.getId())) {
            memoryStore.put(member.getId(), member);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (DatabaseConfig.testMySQLConnection()) {
            String sql = "DELETE FROM members WHERE id=?";
            try (Connection conn = DatabaseConfig.getMySQLConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    memoryStore.remove(id);
                    return true;
                }
            } catch (Exception ignored) {}
        }
        return memoryStore.remove(id) != null;
    }

    @Override
    public Optional<Member> findById(int id) {
        return Optional.ofNullable(memoryStore.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memoryStore.values().stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memoryStore.values());
    }
}
