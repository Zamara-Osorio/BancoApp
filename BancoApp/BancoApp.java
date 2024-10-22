class BancoException extends Exception {
    public BancoException(String mensaje) {
        super(mensaje);
    }
}

class Cuenta {
    private String numeroCuenta;
    private double saldo;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

public class BancoApp {
    public void retirar(Cuenta cuenta, double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto a retirar debe ser positivo");
        }
        if (cuenta.getSaldo() < monto) {
            throw new BancoException("Saldo insuficiente para realizar el retiro");
        }
        cuenta.setSaldo(cuenta.getSaldo() - monto);
    }

    public void depositar(Cuenta cuenta, double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto a depositar debe ser positivo");
        }
        cuenta.setSaldo(cuenta.getSaldo() + monto);
    }

    public void transferir(Cuenta origen, Cuenta destino, double monto) throws BancoException {
        if (monto <= 0) {
            throw new BancoException("El monto a transferir debe ser positivo");
        }
        if (origen.getSaldo() < monto) {
            throw new BancoException("Saldo insuficiente para realizar la transferencia");
        }
        retirar(origen, monto);
        depositar(destino, monto);
    }

    public static void main(String[] args) {
        try {

            BancoApp banco = new BancoApp();
            Cuenta cuenta1 = new Cuenta("1", 1000);
            Cuenta cuenta2 = new Cuenta("2", 500);

            System.out.println("=== Estado inicial ===");
            System.out.println("Cuenta 1: " + cuenta1.getSaldo());
            System.out.println("Cuenta 2: " + cuenta2.getSaldo());

            System.out.println("\n=== Realizando operaciones ===");
            banco.depositar(cuenta1, 500);
            System.out.println("Depósito exitoso en cuenta 1");

            banco.retirar(cuenta2, 200);
            System.out.println("Retiro exitoso de cuenta 2");

            banco.transferir(cuenta1, cuenta2, 300);
            System.out.println("Transferencia exitosa");

            System.out.println("\n=== Estado final ===");
            System.out.println("Cuenta 1: " + cuenta1.getSaldo());
            System.out.println("Cuenta 2: " + cuenta2.getSaldo());

            System.out.println("\n=== Probando operaciones inválidas ===");
            banco.retirar(cuenta1, 5000); // Debería lanzar excepción por saldo insuficiente

        } catch (BancoException e) {
            System.out.println("Error en operación bancaria: " + e.getMessage());
        }
    }
}