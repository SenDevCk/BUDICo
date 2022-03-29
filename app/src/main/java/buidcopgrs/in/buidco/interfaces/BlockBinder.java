package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.BlockData;

public interface BlockBinder {
    void bindBlock(ArrayList<BlockData> blockData);
    void cancleBlockBinding();
}
