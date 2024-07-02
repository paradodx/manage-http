package org.example.managehttp.factory;

import com.example.lattice.ILattice;
import com.example.lattice.provider.URL;
import org.example.managehttp.utils.LatticeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.lattice.Lattice;


@Component
public class LatticeFactory {

    @Autowired
    private LatticeProperties latticeProperties;

    public ILattice createLattice(Integer chainId){
        URL url = new URL(latticeProperties.getHttpUrl());
        return new Lattice(url, chainId, null);
    }

}
