public class Main {
    public static void main(String[] args) {
        if (args.length != 2){
            Log.Log("log.log", "Enter 1 configuration file or chunk size");
            return;
        }
        try {

            Config config = new Config(args[0]);
            int chunk = Integer.parseInt(args[1]);
            Solve.Solve(config, chunk);

        } catch (Exception exception) {
            if (Log.mylogger == null){
                Log.Log("log.log", exception.getMessage());
            }
        }
    }
}