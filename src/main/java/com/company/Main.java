package com.company;

//TODO I passed arguments from idea, i dont know why but i had problem with terminal when im going to compile app...
// here is how to add apllication params using intelij
//Step 1 : Take the Run menu
//Step 2 : Select Edit Configurations
//Step 3 : Fill the Program arguments field
public class Main {


    public static void main(String[] args) {


        validate(args);

        WordFinder object;

        switch (args.length) {
            default:
            case 2:
                object = new WordFinder(args[0], args[1]);
                break;
            case 3:
                object = new WordFinder(args[0], args[1], true);
                break;
            case 4:
                object = new WordFinder(args[0], args[1], false);
                break;
        }

        object.startWorking();


    }

    private static void validate(String[] args) {

        switch (args.length) {
            case 2:
                return;
            case 3:
                if (!args[2].equals("part")) {
                    throw new IllegalArgumentException("arguments are not valid, please re correct argument list");
                }
                break;
            case 4:
                if (!(args[2].equals("part") && args[3].equals("ci"))) {
                    throw new IllegalArgumentException("arguments are not valid, please re correct argument list");
                }
                break;
            default:
                throw new IllegalArgumentException("arguments are not valid, please re correct argument list");


        }
    }
}
