import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

public class HackParser {
    /**
     * Defines type of commands
     */
    enum CommandType {
        A_COMMAND, C_COMMAND, L_COMMAND;
    }

    private Vector<String> file;
    private BufferedReader br;
    private String command;
    private int index;

    public HackParser(BufferedReader br) throws IOException {
        this.br = br;
        file = new Vector<String>();
        index = 0;
        readFile();
    }

    public CommandType commandType() {
        if (command.charAt(0) == '@')
            return CommandType.A_COMMAND;
        else if (command.contains("=") || command.contains(";"))
            return CommandType.C_COMMAND;
        else if (command.charAt(0) == '(' && command.charAt(command.length() - 1) == ')')
            return CommandType.L_COMMAND;
        else
            return null; //Unknown type error
    }

    private void readFile() throws IOException {
        while (br.ready()) {
            command = br.readLine();
            //Ignore comments and empty lines
            if(command != null) {
                if(command.contains("//"))
                    command = command.substring(0, command.indexOf('/'));
                while(command.contains("//") || command.equals(""))
                    command = br.readLine();
                //Getting rid of white space
                command = command.replaceAll("\\s+", "");
            }
            file.add(command);
        }
        br.close();
    }

    /**
     * Sets the next line as the current command to parse
     */
    public void advance() throws IOException {
        command = file.elementAt(index);
        index++;
    }

    /**
     *
     * @return boolean if br is contains more lines
     */
    public boolean hasMoreCommands() throws IOException {
        return index != file.size();
    }

    //Parse Functions

    /**
     * Pre: Current command is an A_COMMAND or L_COMMAND
     * @return symbol
     */
    public String getSymbol() {
        if(commandType().equals(CommandType.L_COMMAND))
            return command.substring(1, command.length() - 1);
        else //A_COMMAND
            return command.substring(1);
    }

    /**
     * Pre: Current command is a C_COMMAND
     * @return dest
     */
    public String getDest() {
        if(command.indexOf('=') > 0)
            return command.substring(0, command.indexOf('='));
        else
            return null;
    }

    /**
     * Pre: Current command is a C_COMMAND
     * @return comp
     */
    public String getComp() {
        if(command.indexOf(';') > 0 )
            return command.substring(0, command.indexOf(';'));
        else
            return command.substring(command.indexOf('=') + 1);
    }

    /**
     * Pre: Current command is a C_COMMAND
     * @return jump
     */
    public String getJump() {
        if(command.indexOf(';') > 0)
            return command.substring(command.indexOf(';') + 1);
        else
            return null;
    }

    public void resetIndex() {
        index = 0;
    }

    public String getCommand() {
        return command;
    }
}
