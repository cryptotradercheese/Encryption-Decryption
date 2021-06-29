package encryptdecrypt;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        CipherSettings settings = new CipherSettings(args);
        CryptoIO cryptoIO = new CryptoIO(settings);
        cryptoIO.print();
    }
}

// class CLIParser
class CipherSettings {
    String mode = "enc";
    String data = "";
    int key = 0;
    String in = "";
    String out = "";
    String alg = "shift";

    public CipherSettings(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    in = args[i + 1];
                    break;
                case "-out":
                    out = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }
    }
}

class CryptoIO {
    private CipherSettings settings = null;
    public CryptoIO(CipherSettings settings) {
        this.settings = settings;
    }

    private String getOutData() {
        String outData = "";
        if (!"".equals(settings.in)) {
            try {
                outData = new String(Files.readAllBytes(Paths.get(settings.in)));
            } catch (IOException e) {
                System.out.println("Error " + e);
            }
        } else {
            outData = settings.data;
        }
        return outData;
    }

    private String getInData() {
        String inData = "";
        CryptoAlgorithmContext algorithm = new CryptoAlgorithmContext();

        if ("shift".equals(settings.alg)) {
            algorithm.setAlgorithm(new ShiftAlgorithm());
        } else if ("unicode".equals(settings.alg)) {
            algorithm.setAlgorithm(new UnicodeAlgorithm());
        }

        String outData = getOutData();
        if ("enc".equals(settings.mode)) {
            inData = algorithm.encrypt(outData, settings.key);
        } else if ("dec".equals(settings.mode)) {
            inData = algorithm.decrypt(outData, settings.key);
        }
        return inData;
    }

    void print() {
        String inData = getInData();

        if ("".equals(settings.out)) {
            System.out.println(inData);
        } else {
            try (FileWriter writer = new FileWriter(settings.out)) {
                writer.write(inData);
            } catch (IOException e) {
                System.out.println("Error " + e);
            }
        }
    }
}



// Strategy pattern
class CryptoAlgorithmContext {
    private CryptoAlgorithm algorithm;

    void setAlgorithm(CryptoAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    String encrypt(String data, int key) {
        return this.algorithm.encrypt(data, key);
    }

    String decrypt(String data, int key) {
        return this.algorithm.decrypt(data, key);
    }
}

interface CryptoAlgorithm {
    String encrypt(String data, int key);
    String decrypt(String data, int key);
}

class ShiftAlgorithm implements CryptoAlgorithm {
    public String encrypt(String data, int key) {
        String cipherText = "";
        for (int i = 0; i < data.length(); i++) {
            char curChar = data.charAt(i);

            if (curChar >= 97 && curChar <= 122) {
                curChar += key;
                while (!(curChar >= 97 && curChar <= 122)) {
                    curChar -= 26;
                }
                //refactor with StringBuilder
            } else if (curChar >= 65 && curChar <= 90) {
                curChar += key;
                while (!(curChar >= 65 && curChar <= 90)) {
                    curChar -= 26;
                }
            }
            cipherText += curChar;
        }
        return cipherText;
    }

    public String decrypt(String data, int key) {
        String decryptedText = "";
        for (int i = 0; i < data.length(); i++) {
            char curChar = data.charAt(i);
            if (curChar >= 97 && curChar <= 122) {
                curChar -= key;
                while (!(curChar >= 97 && curChar <= 122)) {
                    curChar += 26;
                }
                //refactor with StringBuilder
            } else if (curChar >= 65 && curChar <= 90) {
                curChar -= key;
                while (!(curChar >= 65 && curChar <= 90)) {
                    curChar += 26;
                }
            }
            decryptedText += curChar;
        }
        return decryptedText;
    }
}

class UnicodeAlgorithm implements CryptoAlgorithm {
    public String encrypt(String data, int key) {
        String cipherText = "";
        for (int i = 0; i < data.length(); i++) {
            char curChar = data.charAt(i);
            cipherText += (char) (curChar + key);
        }
        return cipherText;
    }

    public String decrypt(String data, int key) {
        String decryptedText = "";
        for (int i = 0; i < data.length(); i++) {
            char curChar = data.charAt(i);
            decryptedText += (char) (curChar - key);
        }
        return decryptedText;
    }
}