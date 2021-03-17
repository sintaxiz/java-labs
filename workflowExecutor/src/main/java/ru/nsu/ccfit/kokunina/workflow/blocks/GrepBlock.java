package ru.nsu.ccfit.kokunina.workflow.blocks;

import ru.nsu.ccfit.kokunina.workflow.BlockType;
import ru.nsu.ccfit.kokunina.workflow.exceptions.WorkflowException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

// grep <word> – выбор из входного текста строк,
// разделенных символами переноса строки, содержащих заданное слово <word>.
// Вход – текст, выход – текст.

public class GrepBlock implements Block {

    @Override
    public List<String> execute(List<String> text, String[] args) throws WorkflowException {
        if (args == null || args.length < 1) {
            throw new WorkflowException("Not enough args for the command");
        }
        if (text == null) {
            return null;
        }
        List<String> textWithKeyWord = new ArrayList<>();
        String keyWord = args[0];
        for (String line : text) {
            if(line.contains(" " + keyWord + " ")) {
                textWithKeyWord.add(line);
            }
        }
        return textWithKeyWord;
    }

    @Override
    public BlockType getType() {
        return BlockType.InputOutput;
    }
}
