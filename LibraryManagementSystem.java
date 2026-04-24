
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Book {
    int id;
    String bookname;
    String author;
    boolean available;

    Book(int id, String bookname, String author) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.available = true;
    }
}

class User {
    int userid;
    String username;

    User(int userid, String username) {
        this.userid = userid;
        this.username = username;
    }
}

class IssueRecord {
    int bookId;
    int userId;
    LocalDate issueDate;
    LocalDate dueDate;

    IssueRecord(int bookId, int userId, LocalDate issueDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
}

public class LibraryManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<IssueRecord> issuedBooks = new ArrayList<>();

    static int noofbooks = 1;
    static int noofusers = 1;

    public static void main(String[] args) {

        while (true) {

            System.out.println("L I B R A R Y");
            System.out.println("1. Add Book\n");
            System.out.println("2. View Books\n");
            System.out.println("3. Register User\n");
            System.out.println("4. Allot a Book\n");
            System.out.println("5. Return Book\n");
            System.out.println("6. Search for a Book\n");
            System.out.println("7. View  All Issued Books\n");
            System.out.println("8. view all  registered users: \n");
            System.out.println("9.exit");

            System.out.println("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    viewBooks();
                    break;

                case 3:
                    registerUser();
                    break;

                case 4:
                    allotBook();
                    break;

                case 5:
                    returnBook();
                    break;

                case 6:
                    searchBook();
                    break;

                case 7:
                    viewallotedbooks();
                    break;
                case 8:
                    viewallusers();
                    break;

                case 9:
                    System.out.println("Thank You...");
                    System.exit(0);

                default:
                    System.out.println("choice is invalid");
            }
        }
    }

    static void addBook() {

        System.out.println("enter the bookid :");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book name: ");
        String bookname = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        books.add(new Book(id, bookname, author));
        noofbooks++;

        System.out.println("Book Added into the library.");
    }

    static void viewBooks() {

        System.out.println("\n B O O K L I S T");

        for (Book b : books) {

            System.out.println(
                    b.id + " | " +
                            b.bookname + " | " +
                            b.author + " | " +
                            (b.available ? "Available" : "Issued"));
        }
    }

    static void registerUser() {
        System.out.println("enter the userId :");
        int userid = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter User Name: ");
        String name = sc.nextLine();

        users.add(new User(userid, name));
        noofusers++;

        System.out.println("User Registration done.");
    }

    static void allotBook() {

        System.out.print("Enter Book ID: ");
        int bookid = sc.nextInt();

        System.out.print("Enter User ID: ");
        int userid = sc.nextInt();

        for (Book b : books) {

            if (b.id == bookid) {

                if (b.available) {

                    b.available = false;

                    LocalDate givendate = LocalDate.now();
                    LocalDate returndate = givendate.plusDays(7);

                    issuedBooks.add(new IssueRecord(bookid, userid, givendate, returndate));

                    System.out.println("Book Alloted Successfully.");
                    System.out.println("Due Date: " + returndate);

                } else {
                    System.out.println("Book Alloted Already.");
                }

                return;
            }
        }

        System.out.println("Book Not Found.");
    }

    static void returnBook() {

        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        for (IssueRecord r : issuedBooks) {

            if (r.bookId == bookId) {

                LocalDate today = LocalDate.now();

                long lateDays = ChronoUnit.DAYS.between(r.dueDate, today);

                double fine = 0;

                if (lateDays > 0) {
                    fine = lateDays * 5;
                }

                for (Book b : books) {
                    if (b.id == bookId) {
                        b.available = true;
                    }
                }

                issuedBooks.remove(r);

                System.out.println("Book Returned .");

                if (fine > 0) {
                    System.out.println("Late Fine = ₹" + fine);
                } else {
                    System.out.println("No Fine.");
                }

                return;
            }
        }

        System.out.println("Book Not Issued.");
    }

    static void searchBook() {

        System.out.print("Enter Title or Author: ");
        String key = sc.nextLine().toLowerCase();

        System.out.println("\n SEARCH RESULT ");

        for (Book b : books) {

            if (b.bookname.toLowerCase().equals(key) ||
                    b.author.toLowerCase().equals(key)) {

                System.out.println(
                        b.id + " | " +
                                b.bookname + " | " +
                                b.author + " | " +
                                (b.available ? "Available" : "Issued"));
            }
        }
    }

    static void viewallotedbooks() {

        System.out.println("\n ALLOTED BOOKS");

        for (IssueRecord r : issuedBooks) {

            System.out.println(
                    "Book ID: " + r.bookId +
                            " | User ID: " + r.userId +
                            " | Issue Date: " + r.issueDate +
                            " | Due Date: " + r.dueDate);
        }
    }
// method to see all the registered users.
    static void viewallusers() {
        System.out.println(" no of users registered currently:");
        for (User user : users) {
            System.out.println("Userid : " + user.userid + " | " + " username :" + user.username);

        }
    }
}
