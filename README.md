# HWR OOP Lecture: Library Project by B-JAMM

This repository contains a student project created for an ongoing lecture on object-oriented
programming with Java/Kotlin at HWR Berlin (summer term 2024).

> :warning: This code is for educational purposes only. Do not rely on it!

## Prerequisites

Installed:

1. IDE of your choice (e.g. IntelliJ IDEA)
2. JDK of choice installed (e.g. through IntelliJ IDEA)
3. Maven installed (e.g. through IntelliJ IDEA)
4. Git installed

## Local Development

This project uses [Apache Maven][maven] as build tool.

To build from your shell (without an additional local installation of Maven), ensure that `./mvnw`
is executable:

```
chmod +x ./mvnw
```

I recommend not to dive into details about Maven at the beginning.
Instead, you can use [just][just] to build the project.
It reads the repositories `justfile` which maps simplified commands to corresponding sensible Maven
calls.

With _just_ installed, you can simply run this command to perform a build of this project and run
all of its tests:

```
just build
```

## Abstract

(The main target in this project is to create a library management system in which visitors can borrow books.)
(The most important features are: "borrow Book", "return Book" and "load Library")
(State the most interesting problems you encountered during the project.)

## Feature List

(For each feature implemented, add a row to the table!)

| Number | Feature            | Tests                  |
|--------|--------------------|------------------------|
| 1      | borrow             | BorrowReturnTest       |
| 2      | returnBook         | BorrowReturnTest       |
| 3      | createNewLibrarian | CreateInstancesTest    |
| 4      | createNewRoom      | CreateInstancesTest    |
| 5      | createNewShelf     | CreateInstancesTest    |
| 6      | createNewVisitor   | CreateInstancesTest    |
| 7      | addBookOnShelf     | AddRemoveBookShelfTest |
| 8      | removeBookOnShelf  | AddRemoveBookShelfTest |
| 9      | restoreBook        | CLIToolsTest           |
| 10     | loadLibrary        | CSVAdapterTest         |
| 11     | saveLibrary        | CSVAdapterTest         |
| 12     | handleCLIQuery     | CLIToolsTest           |


## Additional Dependencies

 # (For each additional dependency your project requires- Add an additional row to the table!)

| Number | Dependency Name | Dependency Description | Why is it necessary? |
|--------|-----------------|------------------------|----------------------|
| 1      | /               | /                      | /                    |

