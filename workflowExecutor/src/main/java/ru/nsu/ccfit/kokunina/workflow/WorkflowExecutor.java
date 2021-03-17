package ru.nsu.ccfit.kokunina.workflow;

import ru.nsu.ccfit.kokunina.workflow.blocks.Block;
import ru.nsu.ccfit.kokunina.workflow.exceptions.BlockNotFoundException;
import ru.nsu.ccfit.kokunina.workflow.exceptions.ParsingException;
import ru.nsu.ccfit.kokunina.workflow.exceptions.WorkflowException;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkflowExecutor {
    private final HashMap<Integer, String> description;
    private int[] blockSequence;

    private static Logger log = Logger.getLogger(WorkflowExecutor.class.getName());

    private void readBlockSequence(String currentLine) {
        blockSequence = Arrays
                .stream(currentLine.replaceAll(" ","").split("->"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    // Constructor reads description and block sequence
    public WorkflowExecutor(InputStream workflowStream) throws ParsingException {

        log.info("Reading description");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(workflowStream));
        DescriptionReader reader = new DescriptionReader(bufferedReader);
        description = reader.getDescription();

        log.info("Description was succesfully read");

        try {
            readBlockSequence(bufferedReader.readLine());
        } catch (IOException e) {
            throw new ParsingException("Can not read sequence. It should be: blockId1->BlockId2->...->...");
        }
    }

    public void execute() throws WorkflowException {

        log.info("Starting execution...");
        log.info("Number of blocks = " + blockSequence.length);


        List<String> text = new ArrayList<>();
        for (int i = 0; i < blockSequence.length; i++) {

            int blockIdx = blockSequence[i];

            // Search for block in description
            String currentBlockDescription = description.get(blockIdx);
            String[] blockNameAndArgs = currentBlockDescription.split(" ");
            String blockName = blockNameAndArgs[0];
            String[] blockArgs = null;
            if (blockNameAndArgs.length > 1) {
                blockArgs = Arrays.copyOfRange(blockNameAndArgs, 1, blockNameAndArgs.length);
            }

            Block currentBlock;
            try {
                currentBlock = BlockFactory.getInstance().getBlock(blockName);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Can not find block with id=" + blockIdx);
                throw new BlockNotFoundException("Can not find block");
            }

            // Checking block type. First block should be output Only, Last Input Only and others Input/Output
            BlockType blockType = currentBlock.getType();
            if (i == 0) {
                if (blockType != BlockType.OutputOnly) {
                    throw new WorkflowException("First block should be Output type.");
                }
            } else
            if (i == blockSequence.length - 1) {
                if (blockType != BlockType.InputOnly) {
                    throw new WorkflowException("Last block should be Input type.");
                }
            } else {
                if (blockType != BlockType.InputOutput) {
                    throw new WorkflowException("The block in the middle should be Input/Output type.");
                }
            }

            try {
                text = currentBlock.execute(text, blockArgs);
            } catch (WorkflowException e) {
                log.log(Level.SEVERE, "Can not execute block " + currentBlock.toString());
                throw new WorkflowException("Can not execute block");
            }

            log.info("Block " + currentBlock.toString() + " was successfully executed");
        }

    }
}
