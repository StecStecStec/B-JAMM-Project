package hwr.oop.library;

import java.nio.file.Files;
import java.util.*;
import java.io.*;
import java.util.stream.Stream;

public class CLI {
    //Im a hacker
    public static void input() {
        String username;
        int role = 0;
        System.out.println("Username");
        Scanner scanner = new Scanner(System.in);
        username = scanner.next();
        scanner.close();
        System.out.println(username);
        role = checkUsername(username);
        if (role == 2) {
            //Librarian
            libarian(username);
        } else if (role == 1) {
            //Visitor
            visitor(username);
        } else {
            System.out.println("Invalid username");
        }
    }

    public static void libarian(String username) {
        int selection = 0;
        Scanner scanner = new Scanner(System.in);


        System.out.println("0. Create new Visitor");
        System.out.println("1. Create new Librarian");
        System.out.println("2. View Users");
        System.out.println("3. Delete User");
        System.out.println("4. Show open payments");
        System.out.println("5. Create new Book");
        System.out.println("6. Restore Book");
        System.out.println("7. View Books");
        System.out.println("8. Borrow Book requests");
        System.out.println("9. Exit");

        selection = scanner.nextInt();
        String result = switch (selection) {
            case 0 -> {
                String visitorUsername,
                        visitorName,
                        visitorSurname,
                        visitorBirthday,
                        visitorEmail;
                System.out.println("Create new visitor:");
                System.out.println("Username");
                visitorUsername = scanner.next();
                System.out.print("Name: ");
                visitorName = scanner.next();
                System.out.print("Surname: ");
                visitorSurname = scanner.next();
                System.out.print("Birthday: ");
                visitorBirthday = scanner.next();
                System.out.print("Email: ");
                visitorEmail = scanner.next();

                Visitor.createNewVisitor(visitorUsername, visitorName, visitorSurname, visitorBirthday, visitorEmail);
                yield "Visitor created";
            }
            case 1 -> {
                String librarianUsername,
                        librarianName,
                        librarianSurname,
                        librarianBirthday;
                System.out.println("Create new librarian:");
                System.out.println("Username");
                librarianUsername = scanner.next();
                System.out.print("Name: ");
                librarianName = scanner.next();
                System.out.print("Surname: ");
                librarianSurname = scanner.next();
                System.out.print("Birthday: ");
                librarianBirthday = scanner.next();

                Librarian.createNewLibrarian(librarianUsername, librarianName, librarianSurname, librarianBirthday);
                yield "Librarian created";
            }
            default -> {
                System.out.println("Invalid selection");
                yield null;
            }
        };
    }


    public static void visitor(String username) {
        int selection = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("0. Search Book");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. View Borrowed Books");
        System.out.println("4. View open payments");
        System.out.println("5. Exit");

        selection = scanner.nextInt();
    }

    public static int checkUsername(String username) {
        String fileName = "\\csvFiles\\csvRoom.csv";
        File file = new File(fileName);

        try (Stream<String> linesStream = Files.lines(file.toPath())) {
            // Check if any line in the CSV file contains the username
            boolean usernameExists = linesStream.anyMatch(line -> line.contains(username));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    public static void main(String[] args) {
        //input();
        String username = "idiot";
        int a = checkUsername(username);
        System.out.println(a);
    }
}