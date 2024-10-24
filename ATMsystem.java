import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMsystem{
    // Account class to handle account details
    public static class Account {
        private String accountNumber;
        private String pin;
        private double balance;

        public Account(String accountNumber, String pin, double balance) {
            this.accountNumber = accountNumber;
            this.pin = pin;
            this.balance = balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            } else {
                throw new IllegalArgumentException("Deposit amount must be positive.");
            }
        }

        public boolean withdraw(double amount) {
            if (amount > balance) {
                return false; // Insufficient funds
            }
            if (amount > 0) {
                balance -= amount;
                return true;
            }
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
    }

    // ATM class to handle ATM operations and user interactions
    public static class ATM {
        private Map<String, Account> accounts = new HashMap<>();
        private Account currentAccount;
        private Scanner scanner = new Scanner(System.in);

        public ATM() {
            // Initialize with some sample accounts
            accounts.put("12345", new Account("12345", "1111", 500.0));
            accounts.put("67890", new Account("67890", "2222", 300.0));
        }

        public void run() {
            while (true) {
                if (authenticate()) {
                    showMainMenu();
                } else {
                    System.out.println("Invalid account number or PIN. Please try again.");
                }
            }
        }

        private boolean authenticate() {
            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            Account account = accounts.get(accountNumber);
            if (account != null && account.getPin().equals(pin)) {
                currentAccount = account;
                return true;
            }
            return false;
        }

        private void showMainMenu() {
            while (true) {
                System.out.println("\nATM Main Menu:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Balance Inquiry");
                System.out.println("4. Exit");

                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                switch (choice) {
                    case 1:
                        deposit();
                        break;
                    case 2:
                        withdraw();
                        break;
                    case 3:
                        balanceInquiry();
                        break;
                    case 4:
                        System.out.println("Exiting... Thank you for using the ATM.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        private void deposit() {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline

            try {
                currentAccount.deposit(amount);
                System.out.println("Deposit successful. New balance: $" + currentAccount.getBalance());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private void withdraw() {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline

            try {
                if (currentAccount.withdraw(amount)) {
                    System.out.println("Withdrawal successful. New balance: $" + currentAccount.getBalance());
                } else {
                    System.out.println("Insufficient funds. Withdrawal failed.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private void balanceInquiry() {
            System.out.println("Current balance: $" + currentAccount.getBalance());
        }

        public static void main(String[] args) {
            ATM atm = new ATM();
            atm.run();
        }
    }
}
