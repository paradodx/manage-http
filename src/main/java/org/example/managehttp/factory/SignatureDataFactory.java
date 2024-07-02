package org.example.managehttp.factory;

import com.example.lattice.model.Transaction;
import com.example.lattice.model.TransactionKt;
import com.example.model.SignatureData;
import com.example.model.SignatureDataKt;
import org.example.managehttp.utils.LatticeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignatureDataFactory {

    @Autowired
    private LatticeProperties latticeProperties;

    public void setSign(Transaction tx, Integer chainId){
        kotlin.Pair<String, SignatureData> dataPair = TransactionKt.sign(tx,
                latticeProperties.getPrivateKeyHex(),
                latticeProperties.getIsGm(),
                chainId,
                false
        );
        tx.setSign(SignatureDataKt.toHex(dataPair.getSecond(), "0x"));
    }
}
