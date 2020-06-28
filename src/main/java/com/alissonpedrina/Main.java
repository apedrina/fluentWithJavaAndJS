package com.alissonpedrina;

public class Main {

    private static String CLI_HOME = System.getProperty("user.dir");

    public static void main(String[] args) {
        System.out.println("CLI_HOME: " + CLI_HOME);

        Cli cli = new Cli();
        cli.execute(cli.buildRequest(args));

    }

    public static String getCliHome() {
        return CLI_HOME;

    }

}
