import  java.util.Scanner;
import java.util.Random;
import  java.time.LocalDate;
import java.time.format.DateTimeFormatter;
class Account{
    private String accountHolder;
    int accountNumber;
    private int  accountBalance;
    public Account(String accountHolder, int accountNumber, int accountBalance){
        this.accountBalance=accountBalance;
        this.accountHolder=accountHolder;
        this.accountNumber =accountNumber;
    }
    String accountInformation(){
        return "account name: "+this.accountHolder +"\n" +"account Balance: "+this.accountBalance;
    }
    String deposit(int amount){
        this.accountBalance += amount;
        return "Successfully deposited kes "+amount+" New balance is Kes "+this.accountBalance;
    }
    String withdraw (int amount){
        if (this.accountBalance < amount){
            return "Failed Insufficient Balance in your account to withdraw kes "+this.accountBalance;
        }
        this.accountBalance -= amount;
        return "Successfully Withdrawn kes "+amount+" New balance is Kes "+this.accountBalance;
    }
    String checkBalance(){
        return "Your balance is kes "+ this.accountBalance;
    }
    int getBalance(){
        return this.accountBalance;
    }
    String setAccountHolder(String name){
        this.accountHolder =name;
        return "Successfully Changed the Account Holders name";
    }
}

class FixedDepositAccount extends Account{
    int monthsToLock;
    LocalDate date;
    String maturityDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public FixedDepositAccount(String accountHolder, int accountNumber, int accountBalance,int monthsToLock){
        super(accountHolder,accountNumber,accountBalance);
        this.monthsToLock=monthsToLock;
        this.date = LocalDate.now().plusMonths(this.monthsToLock);
        this.maturityDate=formatter.format(date);
        System.out.println("Successfully opened a fixed deposit account with a interest rate of 12.5% p.a\nYou will be able to withdraw your funds on: "+formatter.format(date));
    }
    @Override
    public String withdraw(int amount){
        if (date.isBefore(LocalDate.now())){
            return super.withdraw(amount);
        }
        return "You cannot Withdraw funds from your account Yet. You can withdraw on "+formatter.format(date);
    }
    @Override
    public String accountInformation(){
        return super.accountInformation()+"\nDate of maturity: "+formatter.format(date);
    }



}

class Bank{

   String bankName;

    public Bank(String bankName){
        //constructor method
        this.bankName=bankName;
    }
    public Account CreateAccount(int accountNumber, String accountName,int initialAmount){
        Account currentAccount =new Account(accountName,accountNumber,initialAmount);
        System.out.println("Welcome "+accountName+" you have successfully opened an account your balance is kes "+currentAccount.getBalance());
        return currentAccount;
    }
    public FixedDepositAccount CreateAccount(int accountNumber, String accountName,int initialAmount,int months){
        FixedDepositAccount currentAccount=new FixedDepositAccount(accountName,accountNumber,initialAmount,months);
        System.out.println("Welcome "+accountName+" you have successfully opened a savings account with a maturity date on: "+currentAccount.maturityDate+"and your balance is "+currentAccount.getBalance());
        return currentAccount;
    }

    public String performTransaction(int accountNumber, String transactionType, int amount,Account currentAccount){
        if (accountNumber == currentAccount.accountNumber){
            return switch (transactionType) {
                case ("deposit") -> (currentAccount.deposit(amount));
                case ("withdraw") -> (currentAccount.withdraw((amount)));
                default -> "Kindly specify a correct transaction type";
            };

        }
        else {
            return "Account does not exist create account first";
        }
    }
    public String Balance(int accountNumber,Account currentAccount){
        if (currentAccount.accountNumber==accountNumber) {
            return  (currentAccount.checkBalance());
        }
        else{
            return " Account does not exist kindly create account first";
        }

    }
    public String account_info(Account currentAccount){
        return currentAccount.accountInformation();
    }
}
public class Main{
    public static void main(String[] args){
        Scanner scanner= new Scanner(System.in);

        Bank bank =new Bank("JavaBank");
        boolean validInput =false;
        do{
            int choice;
            System.out.println("Welcome to " + bank.bankName + " Please Choose an account type you wish to create to continue\n 1. Normal Account \n 2. Fixed Deposit Account ");
            try{
                choice= scanner.nextInt();
                switch (choice){
                    case 1:
                        normalAccountOperations(bank,scanner);
                        validInput=true;
                        break;
                    case 2:
                        fixedDepositAccountOperations(bank,scanner);
                        validInput=true;
                        break;
                    default:
                        System.out.println("Please choose an option between 1 and 2");



                }
            }
            catch (Exception e){
                System.out.println("Please choose option 1 or option 2");
            }



        }
        while (! validInput);

    }
    static void fixedDepositAccountOperations(Bank bank, Scanner scanner){

        Random random = new Random();
        System.out.println("Provide your full Names");
        scanner.nextLine();
        String accountName= scanner.nextLine();
        System.out.println("How many months do you wish to lock your deposits?");
        int months=scanner.nextInt();
        System.out.println("How much do wish to deposit on your fixed deposit account?");
        int initialBalance=scanner.nextInt();
        int accountNumber = 1000000+random.nextInt(900000);//Generate a random six-digit number that I can use as the account Number
        FixedDepositAccount currentAccount=bank.CreateAccount(accountNumber,accountName,initialBalance,months);
        boolean exit =false;
        do{
            System.out.println("Choose any of the following options \n 1. Deposit \n 2. Withdraw \n 3. Check Balance \n 4. Get account information \n 5. Exit\n");
            int choice = scanner.nextInt();
            switch (choice){
                case (1):
                    boolean valid =false;
                    while (! valid){
                        System.out.println("Please Enter the amount of money you wish to deposit: ");

                        try {
                            int amount = scanner.nextInt();
                            System.out.println(bank.performTransaction(accountNumber,"deposit",amount,currentAccount)+"\n");
                            valid=true;

                        }
                        catch (Exception e){
                            System.out.println("Please enter an Integer");
                            scanner.next();
                        }
                    }
                    break;
                case(2):
                    System.out.println("Please enter the amount of money you wish to withdraw");
                    int depositAmount =scanner.nextInt();
                    System.out.println(bank.performTransaction(accountNumber,"withdraw",depositAmount,currentAccount)+"\n");
                    break;
                case(3):
                    System.out.println(bank.Balance(accountNumber,currentAccount)+"\n");
                    break;
                case (4):
                    System.out.println(bank.account_info(currentAccount));
                    break;
                case(5):
                    exit=true;
                    break;
            }

        }while (! exit);

    }

    static void normalAccountOperations(Bank bank,Scanner scanner){

        Random random = new Random();
        scanner.nextLine();
        System.out.println("Provide your full Names");
        String accountName= scanner.nextLine();
        int accountNumber = 1000000+random.nextInt(900000);//Generate a random six-digit number that I can use as the account Number
        int initialBalance=0;
        Account currentAccount=bank.CreateAccount(accountNumber,accountName,initialBalance);
        boolean exit =false;
        do{
            System.out.println("Choose any of the following options \n 1. Deposit \n 2. Withdraw \n 3. Check Balance \n 4. Get account information \n 5. Exit\n");
            int choice = scanner.nextInt();
            switch (choice){
                case (1):
                    boolean valid =false;
                    while (! valid){
                        System.out.println("Please Enter the amount of money you wish to deposit: ");

                        try {
                            int amount = scanner.nextInt();
                            System.out.println(bank.performTransaction(accountNumber,"deposit",amount,currentAccount)+"\n");
                            valid=true;

                        }
                        catch (Exception e){
                            System.out.println("Please enter an Integer");
                            scanner.next();
                        }
                    }
                    break;
                case(2):
                    System.out.println("Please enter the amount of money you wish to withdraw");
                    int depositAmount =scanner.nextInt();
                    System.out.println(bank.performTransaction(accountNumber,"withdraw",depositAmount,currentAccount)+"\n");
                    break;
                case(3):
                    System.out.println(bank.Balance(accountNumber,currentAccount)+"\n");
                    break;
                case (4):
                    System.out.println(bank.account_info(currentAccount));
                    break;
                case(5):
                    exit=true;
                    break;
            }

        }while (! exit);
    }
}



