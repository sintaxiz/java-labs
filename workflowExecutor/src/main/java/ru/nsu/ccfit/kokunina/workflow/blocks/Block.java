package ru.nsu.ccfit.kokunina.workflow.blocks;

import ru.nsu.ccfit.kokunina.workflow.BlockType;
import ru.nsu.ccfit.kokunina.workflow.exceptions.WorkflowException;

import java.util.List;

public interface Block {
    List<String> execute(List<String> text, String[] args) throws WorkflowException;

    BlockType getType();
}
