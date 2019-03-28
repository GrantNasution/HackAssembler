/**
 * Grant Nasution
 * Assembler for hack
 * 3/27/2019
 */

import java.io.*;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws IOException {
        HackParser parser = new HackParser(new BufferedReader(new FileReader("D:\\Projects\\HackAssembler\\src\\rect.asm")));
        HackCodeModule encoder = new HackCodeModule();
        HackSymbolTable symbolTable = new HackSymbolTable();
        String machineCode = assemble(parser, encoder, symbolTable);
        createHackFile(machineCode);
    }

    public static String assemble(HackParser parser, HackCodeModule encoder, HackSymbolTable symbolTable) throws IOException {
        int varAddr = 16, lineNum = 0;
        String code = new String(), machineCode = new String();
        //First pass through to get line numbers for labels
        while(parser.hasMoreCommands()) {
            parser.advance();
            if(parser.commandType().equals(HackParser.CommandType.L_COMMAND)) {
                String symbol = parser.getSymbol();
                if (!symbolTable.contains(symbol)) {
                    symbolTable.addEntry(symbol, Integer.toBinaryString(lineNum));
                    //Labels do not add to line count
                    lineNum--;
                }
                //Label defined twice
                else {
                    System.out.println("Label defined twice: " + symbol);
                    return new String();
                }
            }
            lineNum++;
        }
        parser.resetIndex();

        //Generate binary encoding
        while (parser.hasMoreCommands()) {
            parser.advance();
            //Parse C command
            if (parser.commandType().equals(HackParser.CommandType.C_COMMAND)) {
                code += "111" + encoder.comp(parser.getComp()) + encoder.dest(parser.getDest()) + encoder.jump(parser.getJump());
            }
            //Parse A
            else if(parser.commandType().equals(HackParser.CommandType.A_COMMAND)){
                code += "0";
                String symbol = parser.getSymbol(), binSymbol = new String();
                //If symbol is an integer then loading an immediate value
                if (symbol.matches("-?\\d+")) {
                    binSymbol = Integer.toBinaryString(Integer.valueOf(symbol));
                    //Concatenate imm value with 0 until size is 15
                    String zero = "0";
                    while (binSymbol.length() < 15) {
                        binSymbol = zero + binSymbol;
                    }
                }
                //If symbol doesn't exists add entry and increment varAddr
                else if (!symbolTable.contains(symbol)) {
                    symbolTable.addEntry(symbol, Integer.toBinaryString(varAddr));
                    varAddr += 2;
                    //Addresses are 15 bits long in instructions
                    binSymbol = symbolTable.getAddress(symbol).substring(1);
                }
                //Symbol exists in symbol table
                else {
                    binSymbol = symbolTable.getAddress(symbol).substring(1);
                }
                code += binSymbol;
            }
            if(!code.equals(""))
                machineCode += code + "\n";
            code = "";
        }
        System.out.println(machineCode);
        return machineCode;
    }

    public static void createHackFile(String machineCode) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Projects\\HackAssembler\\src\\Grant_Nasution rect.hack"));
        writer.write(machineCode);
        writer.close();
    }
}
