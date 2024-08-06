package org.example.managehttp;

import com.example.lattice.ILattice;
import com.example.model.block.TBlock;
import com.example.model.block.TBlockPages;
import com.zkjg.lattice.abi.Function;
import com.zkjg.lattice.abi.Tuple;
import org.bouncycastle.util.encoders.Hex;
import org.example.managehttp.factory.LatticeFactory;
import org.example.managehttp.utils.LatticeProperties;
import org.example.managehttp.utils.FormatUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

@SpringBootTest
public class TxTest {
    @Autowired
    private LatticeProperties latticeProperties;
    @Autowired
    private LatticeFactory latticeFactory;
    
    @Test
    public void TxTest01(){
        ILattice lattice = latticeFactory.createLattice(latticeProperties.getChainId());
        TBlockPages pages = lattice.getTBlockPages(1, 1000);
        // System.out.println(pages);
        List<TBlock> tBlocks = pages.getTBlocks();
        for (TBlock block : tBlocks) {
            String code = block.getCode();
            System.out.println(code);
        }
    }
    
    @Test
    public void TxTest02(){
        ILattice lattice = latticeFactory.createLattice(latticeProperties.getChainId());
        TBlockPages pages = lattice.getTBlockPages(1, 3000);
        List<TBlock> tBlocks = pages.getTBlocks();
        for (TBlock block : tBlocks){
            String code = block.getCode();
            if (code != null && !code.isEmpty()) {
                if (code.startsWith("4131ff53", 2)){
                    System.out.println(block.getTimestamp());
                    System.out.println(block.getVersion());
                }
            }
        }
    }
    
    @Test
    public void TxTest03(){
        String code = "0x4131ff530000000000000000000000000000000000000000000000000000000a0000000100000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000100000000000000000000000000561717f7922a233720ae38acaa4174cda0bf17660000000000000000000000000000000000000000000000000000000000000042307839656136353034666266363537313030346131643366303766316466653361326534333164663230643936623231666461636230333839393036326636393130000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000201000000000a00000001a087a86600000000c59523e4049702ef0a152cfa1026bcfd000000000000000000000000000000000000000000000000000000000000";
        code = FormatUtils.removeHexPrefix(code);
        Function function = new Function("writeTraceability(uint64,string,bytes32[],address)");
        Tuple tuple = function.decodeCall(Hex.decode(code));
        byte[][] data = tuple.get(2);
        byte[] output = FormatUtils.removePaddingZeros(FormatUtils.toByteArray(data));
        byte[] uriBytes = new byte[8];
        System.arraycopy(output, 2, uriBytes, 0, 8);
        String uri = Long.toString(ByteBuffer.wrap(uriBytes).order(ByteOrder.BIG_ENDIAN).getLong());
        System.out.println(uri);
    }
    
    @Test
    public void TxTest04(){
        ILattice lattice = latticeFactory.createLattice(latticeProperties.getChainId());
        TBlockPages pages = lattice.getTBlockPages(1, 1000);
        List<TBlock> tBlocks = pages.getTBlocks();
        for (TBlock block : tBlocks){
            String code = block.getCode();
            if (code != null && !code.isEmpty()) {
                if (code.startsWith("4131ff53", 2)) {
                    System.out.println(pages.getHeight());
                }
            }
        }
    }
}
