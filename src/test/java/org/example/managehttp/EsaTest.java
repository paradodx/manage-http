package org.example.managehttp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.esaulpaugh.headlong.abi.Tuple;
import com.esaulpaugh.headlong.abi.TupleType;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import com.esaulpaugh.headlong.abi.Address;
@SpringBootTest
public class EsaTest {

    @Test
    public void tupleTest(){
        TupleType<Tuple> tt = TupleType.parse("(bool,address,int72[][])");
        ByteBuffer b0 = tt.encode(Tuple.of(false, Address.wrap("0x52908400098527886E0F7030069857D2E4169EE7"), new BigInteger[0][]));
// Tuple t = tt.decode(b0); // decode the tuple (has the side effect of advancing the ByteBuffer's position)
// or...
        Address a = tt.decode(b0, 1); // decode only index 1
        System.out.println(a);
        Tuple t2 = tt.decode(b0, 0, 2); // decode only indices 0 and 2
        System.out.println(t2);

        // ByteBuffer b1 = tt.getNonCapturing(2).encode(new BigInteger[][] {  }); // encode only int72[][]
    }

}
