package firma.support;

public class FirmBancAccount {
    private static Double bankAccount;

    public FirmBancAccount() {
        bankAccount = 200_000.0;
    }

    public static Double getBankAccount() {
        return bankAccount;
    }

    public static void setBankAccount(Double bankAccount) {
        FirmBancAccount.bankAccount = bankAccount;
    }
}
