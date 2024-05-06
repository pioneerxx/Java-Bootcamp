package ex02;

class Program {
    public static void main(String args[]) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.out.println("Wrong input\nUsage: java Program --current-folder=[Folder]");
            System.exit(-1);
        }
        try {
            Finder finder = new Finder(args[0].split("=")[1]);
            finder.startFinder();
        } catch (NotDirectoryException e) {
            System.out.println("The path is not a directory");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Specify the folder");
        }
    }
}