import java.util.concurrent.ThreadLocalRandom;

class Main implements Runnable {
    private String nome;
    private int distanciaPercorrida;
    private int distanciaTotal;

    public Main(String nome, int distanciaTotal) {
        this.nome = nome;
        this.distanciaPercorrida = 0;
        this.distanciaTotal = distanciaTotal;
    }

    private void acelerar() {
        int aceleracao = ThreadLocalRandom.current().nextInt(5, 16);
        distanciaPercorrida += aceleracao;
    }

    private boolean chegouLinhaChegada() {
        return distanciaPercorrida >= distanciaTotal;
    }

    @Override
    public void run() {
        try {
            while (!chegouLinhaChegada()) {
                acelerar();
                Thread.sleep(1000);
                System.out.printf("%s andou %dm\t e ja percorreu %dm%n", nome, distanciaPercorrida, distanciaPercorrida);
            }
            System.out.println(nome + " alcancou a linha de chegada!");
        } catch (InterruptedException e) {
            System.out.println("A thread do " + nome + " foi interrompida.");
        }
    }

    public static void main(String[] args) {
        Thread[] carros = new Thread[5];

        for (int i = 0; i < 5; i++) {
            Thread carro = new Thread(new Main(String.format("[Carro_%d]", i), 200));
            carros[i] = carro;
        }

        for (Thread t : carros) {
            t.start();
        }
    }
}