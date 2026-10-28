package com.nextgen.week2.concepts;

/**
 * Week 2 Concept: Encapsulation
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Data hiding using private access modifiers
 * - Public getter and setter methods with strict validation
 * - Read-only fields (immutable properties)
 */
public class EncapsulationDemo {

    public static class UserProfile {
        private final String userId;      // Read-only / immutable once set
        private String fullName;
        private String email;
        private int age;

        public UserProfile(String userId, String fullName, String email, int age) {
            if (userId == null || userId.trim().isEmpty()) {
                throw new IllegalArgumentException("User ID cannot be empty");
            }
            this.userId = userId;
            setFullName(fullName);
            setEmail(email);
            setAge(age);
        }

        public String getUserId() {
            return userId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            if (fullName == null || fullName.trim().length() < 2) {
                throw new IllegalArgumentException("Full name must be at least 2 characters");
            }
            this.fullName = fullName.trim();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            if (email == null || !email.contains("@") || !email.contains(".")) {
                throw new IllegalArgumentException("Invalid email format: " + email);
            }
            this.email = email.trim();
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            if (age < 18 || age > 100) {
                throw new IllegalArgumentException("Age must be between 18 and 100");
            }
            this.age = age;
        }

        @Override
        public String toString() {
            return String.format("UserProfile[ID=%s, Name='%s', Email='%s', Age=%d]",
                    userId, fullName, email, age);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("      WEEK 2: ENCAPSULATION DEMONSTRATION         ");
        System.out.println("==================================================");

        UserProfile profile = new UserProfile("USR-101", "Aarav Sharma", "aarav@nextgenhub.in", 22);
        System.out.println("Created profile: " + profile);

        System.out.println("\nUpdating valid email...");
        profile.setEmail("aarav.sharma@nextgenhub.in");
        System.out.println("Updated profile: " + profile);

        System.out.println("\nAttempting invalid age update (setting age to 10):");
        try {
            profile.setAge(10);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught successfully: " + e.getMessage());
        }

        System.out.println("\nAttempting invalid email update (setting invalid format):");
        try {
            profile.setEmail("not-an-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught successfully: " + e.getMessage());
        }

        System.out.println("\nFinal State protected by encapsulation: " + profile);
    }
}
