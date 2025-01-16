package a05.e1;

public class BankAccountFactoryImpl implements BankAccountFactory {

    @Override
    public BankAccount simple() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {}

            @Override
            protected void onDisallowedWithdraw() {}

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return balance() >= amount;
            }
            
        };
    }

    @Override
    public BankAccount withFee(int fee) {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {}

            @Override
            protected void onDisallowedWithdraw() {}

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount - fee;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return balance() >= amount + fee;
            }
            
        };
    }

    @Override
    public BankAccount checked() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return amount >= 0;
            }

            @Override
            protected void onDisallowedDeposit() {
                throw new IllegalStateException();
            }

            @Override
            protected void onDisallowedWithdraw() {
                throw new IllegalStateException();
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return balance() >= amount && amount >= 0;
            }
            
        };
    }

    @Override
    public BankAccount gettingBlocked() {
        return new AbstractBankAccount() {
            private boolean blocked = false;

            @Override
            protected boolean canDeposit(int amount) {
                return amount >= 0 && !blocked;
            }

            @Override
            protected void onDisallowedDeposit() {
                blocked = true;
            }

            @Override
            protected void onDisallowedWithdraw() {
                blocked = true;
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return balance() >= amount && amount >= 0 && !blocked;
            }

        };
    }

    @Override
    public BankAccount pool(BankAccount primary, BankAccount secondary) {
        return new BankAccount() {

            @Override
            public int balance() {
                return primary.balance() + secondary.balance();
            }

            @Override
            public void deposit(int amount) {
                if (primary.balance() <= secondary.balance()) {
                    primary.deposit(amount);
                } else {
                    secondary.deposit(amount);
                }
            }

            @Override
            public boolean withdraw(int amount) {
                if (primary.balance() >= amount) {
                    primary.withdraw(amount);
                } else if (secondary.balance() >= amount) {
                    secondary.withdraw(amount);
                } else {
                    return false;
                }
                return true;
            }
            
        };
    }

}
