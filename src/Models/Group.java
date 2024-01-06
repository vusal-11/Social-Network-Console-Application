package Models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private static int groupCounter = 0;
    private int groupID;
    private String name;
    private List<User> members;
    private User admin;
    private boolean hasAdmin = false;


    public boolean hasAdmin() {
        return hasAdmin;
    }

    public void setHasAdmin(boolean hasAdmin) {
        this.hasAdmin = hasAdmin;
    }


    public Group(String name) {
        this.groupID = ++groupCounter;
        this.name = name;
        this.members = new ArrayList<>();
    }

    public int getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public User getAdmin() {
        return admin;
    }

    public void addMember(User user) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        }
        if (!this.members.contains(user)) {
            this.members.add(user);
        }
    }

    public void removeMember(User user) {
        if (members.contains(user)) {
            members.remove(user);
        } else {
            System.out.println("The user is not a member of this group.");
        }
    }

    public void assignAdmin(User newAdmin) {
        if (members.contains(newAdmin)) {
            this.admin = newAdmin;
        } else {
            System.out.println("The user must be a member of the group to become an administrator.");
        }
    }

    public void removeAdmin() {
        if (this.admin != null) {
            System.out.println(this.admin.getUsername() + " removed from the group administration." + this.name);
            this.admin = null;
        } else {
            System.out.println("Administrator is not assigned to this group.");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
