package com.company;

//TODO I passed arguments from idea, i dont know why but i had problem with terminal when im going to compile app...
// here is how to add apllication params using intelij
//Step 1 : Take the Run menu
//Step 2 : Select Edit Configurations
//Step 3 : Fill the Program arguments field
public class Main {
    public static String keyWord;
    public static String fileName;

    public static void main(String[] args) {

        if (!validate(args)) {
            throw new IllegalArgumentException("arguments are not valid, please re correct argument list");
        }
        keyWord = args[0];
        fileName = args[1];

        switch (args.length) {
            case 2:
                WordFinder.startWorking();
                break;
            case 3:
                WordFinder.startWorking(false);
                break;
            case 4:
                WordFinder.startWorking(true, true);
                break;
        }

    }

    private static boolean validate(String[] args) {

        switch (args.length) {
            case 2:
                return true;
            case 3:
                return args[2].equals("part");
            case 4:
                return args[2].equals("part") && args[3].equals("ci");
            default:
                return false;

        }
    }
}
