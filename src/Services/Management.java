package Services;
import Models.Comment;
import Models.Group;
import Models.Post;
import Models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Management {
    private static final LoginService loginService = new LoginService();
    private static final Scanner scanner = new Scanner(System.in);

    private static User currentUser;


    private static List<Group> groups = new ArrayList<>();

    private static Group group;






    public static void run() {
        System.out.println("Welcome to the Social Network Console Application!");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = new User(username.hashCode(), username, password);
        loginService.register(currentUser);
        System.out.print("user '"+currentUser.getUsername()+"' "+ "registered successfully !!!\n");

    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (loginService.authenticate(username, password)) {
            userMenu();
        } else {
            System.out.println("Login failed. Please try again.");
        }


    }

    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Group Management");
            System.out.println("2. Post Management");
            System.out.println("3. Comment Management");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // очистка буфера

            switch (choice) {
                case 1:
                    groupManagement();
                    break;
                case 2:
                    postManagement();
                    break;
                case 3:
                    manageCommentsMenu();
                case 4:
                    currentUser = null;
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }
    }

    private static void groupManagement() {
        while (true) {
            System.out.println("\nGroup Management");
            System.out.println("1. Create Group");
            System.out.println("2. View Groups");
            System.out.println("3. Join Group");
            System.out.println("4. Assign Admin");
            System.out.println("5. Left Group");
            System.out.println("6. Remove Admin");
            System.out.println("7. Return to Previous Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createGroup();
                    break;
                case 2:
                    viewGroups();
                    break;
                case 3:
                    joinGroup();
                    break;
                case 4:
                    assignAdmin();
                    break;
                case 5:
                    leftGroup();
                    break;
                case 6:
                    removeAdmin();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }

    }

    private static void createGroup() {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine();
        groups.add(group=new  Group(groupName));
        System.out.println("Group '" + groupName + "' created successfully!");
    }

    private static void viewGroups() {

        if (groups.isEmpty()) {
            System.out.println("There are no groups available.");
        } else {
            System.out.println("List of Groups:");
            for (Group group : groups) {
                System.out.println(group.getName());
            }
        }
    }

    private static void joinGroup() {
        if (groups.isEmpty()) {
            System.out.println("No groups here to join. For the first, you need to create one.");
            return;
        }

        int groupNumber = 1;
        for (Group group : groups) {
            System.out.println(group.getName() + " -> " + groupNumber);
            groupNumber++;
        }

        System.out.print("Enter group number to join: ");
        int selectedGroupNumber = scanner.nextInt();

        if (selectedGroupNumber < 1 || selectedGroupNumber > groups.size()+1) {
            System.out.println("Invalid group number. Please try again.");
            return;
        }

        Group selectedGroup = groups.get(selectedGroupNumber - 1);

        if (selectedGroup.getMembers() != null) {
            for (User member : selectedGroup.getMembers()) {
                if (member.getUsername().equals(currentUser.getUsername())) {
                    System.out.println("You are already a member of this group.");
                    return;
                }
            }
        }

        selectedGroup.addMember(currentUser);
        System.out.println("Joined group '" + selectedGroup.getName() + "' successfully!");

    }

    private static void assignAdmin(){
        if (groups.isEmpty()) {
            System.out.println("There are no groups available to assign an admin.");
            return;
        }

        System.out.println("Choose the group to assign Admin:");
        for (int i = 0; i < groups.size(); i++) {
            System.out.println(groups.get(i).getName() + " -> Press - " + (i + 1));
        }
        int groupChoice = scanner.nextInt();
        if (groupChoice <= 0 || groupChoice > groups.size()) {
            System.out.println("Invalid group choice!");
            return;
        }
        int choice = groupChoice - 1;

        List<User> members = groups.get(choice).getMembers();

        if (members.isEmpty()) {
            System.out.println("There are no members in the selected group to assign an admin.");
            return;
        }


        boolean hasAdmin = false;
        for (User member : members) {
            if (member.isAdmin()) {
                hasAdmin = true;
                break;
            }
        }

        if (hasAdmin) {
            System.out.println("There is already an administrator in the group " + groups.get(choice).getName() + ".");
            System.out.println("Do you still want to assign the user as an admin? (yes/no)");

            String confirm = scanner.next().toLowerCase();
            if (!"yes".equals(confirm)) {
                System.out.println("Operation canceled.");
                return;
            }
        }

        System.out.println("Choose the user to assign Admin:");

        for (int i = 0; i < members.size(); i++) {
            System.out.println(members.get(i).getUsername() + " -> Press - " + (i + 1));
        }
        int userChoice = scanner.nextInt();
        if (userChoice <= 0 || userChoice > members.size()) {
            System.out.println("Invalid user choice!");
            return;
        }
        int choiceUser = userChoice - 1;

        groups.get(choice).assignAdmin(members.get(choiceUser));


        System.out.println(members.get(choiceUser).getUsername() + " has been assigned as an admin of " + groups.get(choice).getName() + " group.");


    }


    private static void leftGroup(){
        String currentUsername = currentUser.getUsername();

        if (groups.isEmpty()) {
            System.out.println("There are no groups available to leave.");
            return;
        }

        boolean userFoundAndRemoved = false;
        for (Group group : groups) {
            List<User> members = group.getMembers();


            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getUsername().equals(currentUsername)) {
                    members.remove(i);
                    System.out.println("User has been removed from the group: " + group.getName());
                    userFoundAndRemoved = true;
                    break;
                }
            }
        }

        if (!userFoundAndRemoved) {
            System.out.println("User not found in any groups.");
        }
    }

    private static void removeAdmin(){
        String currentUsername = currentUser.getUsername();
        boolean foundAdmin = false;

        for (Group group : groups) {
            List<User> members = group.getMembers();

            for (User user : members) {
                if (user.getUsername().equals(currentUsername) && user.isAdmin()) {
                    foundAdmin = true;
                    break;
                }
            }

            if (foundAdmin) {
                for (User user : members) {
                    if (user.getUsername().equals(currentUsername)) {
                        user.setAdmin(false);
                        System.out.println("Administrator successfully removed from the group: " + group.getName());
                        return;
                    }
                }
            }
        }

        if (!foundAdmin) {
            System.out.println("You are not an administrator in any of the groups.");
        }
    }



    private static User findUserByUsername(String username) {
        for (User user : loginService.getRegisteredUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    private static void postManagement() {
        while (true){
            System.out.println("\nPost Management");
            System.out.println("1. Create Post");
            System.out.println("2. View My Posts");
            System.out.println("3. Edit Post");
            System.out.println("4. Delete Post");
            System.out.println("5. Return to Previous Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createPost();
                    break;
                case 2:
                    viewMyPosts();
                    break;
                case 3:
                    editMyPosts();
                    break;
                case 4:
                    deletePost();
                case 5:
                    return;
            }
        }

    }
    private static void createPost() {
        System.out.print("Enter post content to create: ");
        String content = scanner.nextLine();


        User authorUser = findUserByUsername(currentUser.getUsername());
        if (authorUser != null) {
            Post newPost = new Post(1, content, authorUser);
            currentUser.getPosts().add(newPost);
            System.out.println("Post created successfully!");
        } else {
            System.out.println("Author user not found.");
        }
    }
    private static void viewMyPosts() {
        List<Post> userPosts = currentUser.getPosts();

        if (userPosts.isEmpty()) {
            System.out.println("You have no posts.");
            return;
        }

        System.out.println("Your posts:");
        for (Post post : userPosts) {
            System.out.println("- " + post.getContent());
        }
    }
    private static void editMyPosts() {
        if (currentUser.getPosts().isEmpty()) {
            System.out.println("You have no posts to edit.");
            return;
        }

        System.out.println("Your posts:");
        for (int i = 0; i < currentUser.getPosts().size(); i++) {
            System.out.println(currentUser.getPosts().get(i).getContent() + " -> " + (i + 1));
        }
        System.out.println();
        System.out.print("Enter post number to edit: ");
        int postToEditIndex = scanner.nextInt() - 1;

        if (postToEditIndex < currentUser.getPosts().size() && postToEditIndex >= 0) {
            System.out.print("Enter new content for the post: ");
            scanner.nextLine();

            String newContent = scanner.nextLine();
            currentUser.getPosts().get(postToEditIndex).setContent(newContent);
            System.out.println("Post edited successfully!");
        } else {
            System.out.println("Invalid post number. Please try again.");
        }
    }

    private static void deletePost() {
        if (currentUser.getPosts().isEmpty()) {
            System.out.println("You have no posts to delete.");
            return;
        }

        System.out.println("Your posts:");
        for (int i = 0; i < currentUser.getPosts().size(); i++) {
            System.out.println(currentUser.getPosts().get(i).getContent() + " -> " + (i + 1));
        }
        System.out.println();
        System.out.print("Enter post number to delete: ");
        int postToDeleteIndex = scanner.nextInt() - 1;

        if (postToDeleteIndex < currentUser.getPosts().size() && postToDeleteIndex >= 0) {
            currentUser.getPosts().remove(postToDeleteIndex);
            System.out.println("Post deleted successfully!");
        } else {
            System.out.println("Invalid post number. Please try again.");
        }
    }
    private static void manageCommentsMenu() {
        while (true) {
            System.out.println("\nComment Management");
            System.out.println("1. Add Comment");
            System.out.println("2. Edit Comment");
            System.out.println("3. Delete Comment");
            System.out.println("4. Return to Previous Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCommentToPost();
                    break;
                case 2:
                    editComment();
                    break;
                case 3:
                    deleteComment();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }
    }
    private static void addCommentToPost() {
        if (currentUser.getPosts().isEmpty()) {
            System.out.println("You have no posts to add a comment to.");
            return;
        }

        System.out.println("Your posts:");
        for (int i = 0; i < currentUser.getPosts().size(); i++) {
            System.out.println(currentUser.getPosts().get(i).getContent() + " -> " + (i + 1));
        }
        System.out.println();
        System.out.print("Enter post number to add comment: ");
        int postToEditIndex = scanner.nextInt() - 1;

        scanner.nextLine();

        if (postToEditIndex < currentUser.getPosts().size() && postToEditIndex >= 0) {
            System.out.print("Enter new comment for the post: ");
            String newContent = scanner.nextLine();
            currentUser.getPosts().get(postToEditIndex).addComment(new Comment(newContent, currentUser.getPosts().get(postToEditIndex)));
            System.out.println("Comment added successfully!");
        } else {
            System.out.println("Invalid post number. Please try again.");
        }
    }
    private static void editComment() {
        if (currentUser.getPosts().isEmpty()) {
            System.out.println("You have no posts to edit comments for.");
            return;
        }
        System.out.println("Your posts:");
        for (int i = 0; i < currentUser.getPosts().size(); i++) {
            System.out.println(currentUser.getPosts().get(i).getContent() + " -> " + (i + 1));
        }
        System.out.println();
        System.out.print("Enter post number to edit comment: ");
        int postToEditIndex;
        if (scanner.hasNextInt()) {
            postToEditIndex = scanner.nextInt() - 1;
            scanner.nextLine();
        } else {
            System.out.println("Invalid input! Please enter a valid integer for the post number.");
            scanner.nextLine();
            return;
        }

        if (postToEditIndex < 0 || postToEditIndex >= currentUser.getPosts().size()) {
            System.out.println("Invalid post number!");
            return;
        }

        List<Comment> postComments = currentUser.getPosts().get(postToEditIndex).getComments();
        if (postComments.isEmpty()) {
            System.out.println("There are no comments for this post to edit!");
            return;
        }

        System.out.println("Comments for selected post:");
        for (int i = 0; i < postComments.size(); i++) {
            System.out.println(postComments.get(i).getContent() + " " + postComments.get(i).getCommentID());
        }
        System.out.print("Enter comment number to edit: ");
        int commentIndex;
        if (scanner.hasNextInt()) {
            commentIndex = scanner.nextInt() - 1;
            scanner.nextLine();
        } else {
            System.out.println("Invalid input! Please enter a valid integer for the comment number.");
            scanner.nextLine();
            return;
        }

        if (commentIndex < 0 || commentIndex >= postComments.size()) {
            System.out.println("Invalid comment number!");
            return;
        }

        System.out.print("Enter new content for the comment: ");
        String newContent = scanner.nextLine();

        currentUser.getPosts().get(postToEditIndex).editComment(postComments.get(commentIndex).getCommentID(), newContent);
        System.out.println("Comment edited successfully!");
    }

    private static void deleteComment() {
        if (currentUser.getPosts().isEmpty()) {
            System.out.println("You have no posts to delete comments from.");
            return;
        }

        System.out.println("Your posts:");
        for (int i = 0; i < currentUser.getPosts().size(); i++) {
            System.out.println(currentUser.getPosts().get(i).getContent() + " -> " + (i + 1));
        }

        System.out.println();
        System.out.print("Enter post number to delete comment: ");

        int postToEditIndex;
        if (scanner.hasNextInt()) {
            postToEditIndex = scanner.nextInt() - 1;
            scanner.nextLine();
        } else {
            System.out.println("Invalid input! Please enter a valid integer for post number.");
            scanner.nextLine();
            return;
        }

        if (postToEditIndex < 0 || postToEditIndex >= currentUser.getPosts().size()) {
            System.out.println("Invalid post number!");
            return;
        }

        List<Comment> postComments = currentUser.getPosts().get(postToEditIndex).getComments();

        if (postComments.isEmpty()) {
            System.out.println("There are no comments for this post to delete!");
            return;
        }

        System.out.println("Comments for selected post:");
        for (int i = 0; i < postComments.size(); i++) {
            System.out.println(postComments.get(i).getContent() + " " + postComments.get(i).getCommentID());
        }

        System.out.print("Enter comment number to delete: ");

        int commentIndex;
        if (scanner.hasNextInt()) {
            commentIndex = scanner.nextInt() - 1;
            scanner.nextLine();
        } else {
            System.out.println("Invalid input! Please enter a valid integer for comment number.");
            scanner.nextLine();
            return;
        }

        if (commentIndex < 0 || commentIndex >= postComments.size()) {
            System.out.println("Invalid comment number!");
            return;
        }

        currentUser.getPosts().get(postToEditIndex).deleteComment(commentIndex);
        System.out.println("Comment deleted successfully!");
    }

}
