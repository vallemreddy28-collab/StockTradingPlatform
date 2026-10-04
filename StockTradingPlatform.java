import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public void displayStock() {
        System.out.println(
            symbol + " | " + companyName + " | Price: Rs." + price
        );
    }
}

class Transaction {
    private String type;
    private String stockSymbol;
    private int quantity;
    private double price;

    public Transaction(String type, String stockSymbol,
                       int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    public void displayTransaction() {
        System.out.println(
            type + " | Stock: " + stockSymbol +
            " | Quantity: " + quantity +
            " | Price: Rs." + price
        );
    }
}

class User {
    private String name;
    private double balance;

    private ArrayList<String> portfolioStocks;
    private ArrayList<Integer> portfolioQuantity;
    private ArrayList<Transaction> transactions;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        portfolioStocks = new ArrayList<>();
        portfolioQuantity = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void buyStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        double totalCost = stock.getPrice() * quantity;

        if (balance < totalCost) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance = balance - totalCost;

        int index = portfolioStocks.indexOf(stock.getSymbol());

        if (index == -1) {
            portfolioStocks.add(stock.getSymbol());
            portfolioQuantity.add(quantity);
        } else {
            int oldQuantity = portfolioQuantity.get(index);
            portfolioQuantity.set(index, oldQuantity + quantity);
        }

        transactions.add(
            new Transaction(
                "BUY",
                stock.getSymbol(),
                quantity,
                stock.getPrice()
            )
        );

        System.out.println("Stock purchased successfully!");
        System.out.println("Total cost: Rs." + totalCost);
        System.out.println("Remaining balance: Rs." + balance);
    }

    public void sellStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        int index = portfolioStocks.indexOf(stock.getSymbol());

        if (index == -1) {
            System.out.println("You do not own this stock.");
            return;
        }

        int ownedQuantity = portfolioQuantity.get(index);

        if (quantity > ownedQuantity) {
            System.out.println("You do not have enough shares.");
            return;
        }

        double totalAmount = stock.getPrice() * quantity;

        balance = balance + totalAmount;

        int remaining = ownedQuantity - quantity;

        if (remaining == 0) {
            portfolioStocks.remove(index);
            portfolioQuantity.remove(index);
        } else {
            portfolioQuantity.set(index, remaining);
        }

        transactions.add(
            new Transaction(
                "SELL",
                stock.getSymbol(),
                quantity,
                stock.getPrice()
            )
        );

        System.out.println("Stock sold successfully!");
        System.out.println("Amount received: Rs." + totalAmount);
        System.out.println("Current balance: Rs." + balance);
    }

    public void displayPortfolio(ArrayList<Stock> stocks) {

        System.out.println("\n========== PORTFOLIO ==========");

        if (portfolioStocks.isEmpty()) {
            System.out.println("No stocks in portfolio.");
            return;
        }

        double portfolioValue = 0;

        for (int i = 0; i < portfolioStocks.size(); i++) {

            String symbol = portfolioStocks.get(i);
            int quantity = portfolioQuantity.get(i);

            Stock stock = findStock(stocks, symbol);

            if (stock != null) {

                double value = stock.getPrice() * quantity;
                portfolioValue = portfolioValue + value;

                System.out.println(
                    symbol +
                    " | Quantity: " + quantity +
                    " | Current Price: Rs." + stock.getPrice() +
                    " | Value: Rs." + value
                );
            }
        }

        System.out.println("--------------------------------");
        System.out.println("Cash Balance: Rs." + balance);
        System.out.println("Stock Value: Rs." + portfolioValue);
        System.out.println(
            "Total Portfolio Value: Rs." +
            (balance + portfolioValue)
        );
    }

    public void displayTransactions() {

        System.out.println("\n========== TRANSACTIONS ==========");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }
    }

    private Stock findStock(
        ArrayList<Stock> stocks,
        String symbol
    ) {

        for (Stock stock : stocks) {

            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Stock> stocks = new ArrayList<>();

        stocks.add(
            new Stock(
                "TCS",
                "Tata Consultancy Services",
                3500
            )
        );

        stocks.add(
            new Stock(
                "INFY",
                "Infosys",
                1500
            )
        );

        stocks.add(
            new Stock(
                "RELIANCE",
                "Reliance Industries",
                2800
            )
        );

        stocks.add(
            new Stock(
                "HDFC",
                "HDFC Bank",
                1700
            )
        );

        stocks.add(
            new Stock(
                "WIPRO",
                "Wipro",
                600
            )
        );

        User user = new User("Kiranmayi", 100000);

        int choice;

        System.out.println("======================================");
        System.out.println("       STOCK TRADING PLATFORM");
        System.out.println("======================================");

        System.out.println("Welcome, " + user.getName());
        System.out.println("Starting Balance: Rs." + user.getBalance());

        do {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:

                    System.out.println(
                        "\n========== MARKET DATA =========="
                    );

                    for (Stock stock : stocks) {
                        stock.displayStock();
                    }

                    break;

                case 2:

                    System.out.print("Enter stock symbol: ");
                    String buySymbol = scanner.next();

                    Stock buyStock =
                        findStock(stocks, buySymbol);

                    if (buyStock == null) {
                        System.out.println("Stock not found!");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();

                    user.buyStock(buyStock, buyQuantity);

                    break;

                case 3:

                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = scanner.next();

                    Stock sellStock =
                        findStock(stocks, sellSymbol);

                    if (sellStock == null) {
                        System.out.println("Stock not found!");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();

                    user.sellStock(sellStock, sellQuantity);

                    break;

                case 4:

                    user.displayPortfolio(stocks);

                    break;

                case 5:

                    user.displayTransactions();

                    break;

                case 6:

                    System.out.println(
                        "\nThank you for using Stock Trading Platform!"
                    );

                    break;

                default:

                    System.out.println(
                        "Invalid choice. Please try again."
                    );
            }

        } while (choice != 6);

        scanner.close();
    }

    public static Stock findStock(
        ArrayList<Stock> stocks,
        String symbol
    ) {

        for (Stock stock : stocks) {

            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }
}