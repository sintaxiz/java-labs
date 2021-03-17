package ru.nsu.ccfit.kokunina.workflow.blocks;

import ru.nsu.ccfit.kokunina.workflow.BlockType;
import ru.nsu.ccfit.kokunina.workflow.exceptions.WorkflowException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

// readfile <filename>  – считывание текстового файла в память, целиком.
// Вход – отсутствует, выход – текст.

public class ReadFileBlock implements Block {

    @Override
    public List<String> execute(List<String> text, String[] args) throws WorkflowException {
        if (args == null || args.length < 1) {
            throw new WorkflowException("Not enough args for the command");
        }
        try (FileReader fileReader = new FileReader(args[0]);BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String currLine = bufferedReader.readLine();
            while (currLine != null) {
                text.add(currLine);
                currLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WorkflowException("Can not read line");
        }

        return text;
    }

    @Override
    public BlockType getType() {
        return BlockType.OutputOnly;
    }
}
