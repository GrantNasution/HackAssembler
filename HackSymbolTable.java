import java.util.HashMap;

public class HackSymbolTable {
    private HashMap<String, String> symbolTable;

    public HackSymbolTable() {
        symbolTable = new HashMap<String, String>();
        populate();
    }

    /**
     * Populates symbol table with predifined symbols
     */
    private void populate() {
        symbolTable.put("SP",     "0000000000000000");
        symbolTable.put("LCL",    "0000000000000001");
        symbolTable.put("ARG",    "0000000000000010");
        symbolTable.put("THIS",   "0000000000000011");
        symbolTable.put("THAT",   "0000000000000100");
        symbolTable.put("R0",     "0000000000000000");
        symbolTable.put("R1",     "0000000000000001");
        symbolTable.put("R2",     "0000000000000010");
        symbolTable.put("R3",     "0000000000000011");
        symbolTable.put("R4",     "0000000000000100");
        symbolTable.put("R5",     "0000000000000101");
        symbolTable.put("R6",     "0000000000000110");
        symbolTable.put("R7",     "0000000000000111");
        symbolTable.put("R8",     "0000000000001000");
        symbolTable.put("R9",    "0000000000001001");
        symbolTable.put("R10",    "0000000000001010");
        symbolTable.put("R11",    "0000000000001011");
        symbolTable.put("R12",    "0000000000001100");
        symbolTable.put("R13",    "0000000000001101");
        symbolTable.put("R14",    "0000000000001110");
        symbolTable.put("R15",    "0000000000001111");
        symbolTable.put("SCREEN", "0100000000000000");
        symbolTable.put("KBD",    "0110000000000000");

    }

    /**
     * Pre: Symbol must not already exist in the symbol table
     * @param symbol
     * @param address
     */
    public void addEntry(String symbol, String address) {
        //If append 0's if address is less than 16
        while(address.length() < 16)
            address = "0" + address;
        //If address > 16 bits than concatenate
        if(address.length() > 16)
            address = address.substring(address.length() - 16);
        symbolTable.put(symbol, address);
    }

    public boolean contains(String symbol) {
        if(symbolTable.get(symbol) != null)
            return true;
        else
            return false;
    }

    /**
     * Pre: Symbol must already exist in the symbol table
     * @param symbol
     * @return
     */
    public String getAddress(String symbol) {
        return symbolTable.get(symbol);
    }

}
