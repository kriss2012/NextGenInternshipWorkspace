package com.nextgen.week4.model;

import java.time.LocalDate;

/**
 * Member entity model.
 */
public class Member {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String membershipDate;

    public Member() {}

    public Member(int id, String name, String email, String phone, String membershipDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = membershipDate;
    }

    public Member(String name, String email, String phone) {
        this(0, name, email, phone, LocalDate.now().toString());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMembershipDate() { return membershipDate; }
    public void setMembershipDate(String membershipDate) { this.membershipDate = membershipDate; }

    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"membershipDate\":\"%s\"}",
                id, escape(name), escape(email), escape(phone), escape(membershipDate));
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        return String.format("[#%d] %s | Email: %s | Phone: %s | Joined: %s",
                id, name, email, phone, membershipDate);
    }
}
