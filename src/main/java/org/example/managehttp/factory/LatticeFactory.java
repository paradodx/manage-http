package org.example.managehttp.factory;

import com.example.lattice.ILattice;
import com.example.lattice.provider.URL;
import org.example.managehttp.utils.LatticeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.lattice.Lattice;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class LatticeFactory {

    @Autowired
    private LatticeProperties latticeProperties;

    public ILattice createLattice() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> urlClass = Class.forName("com.example.lattice.provider.URL");
        Constructor<?> constructor = urlClass.getDeclaredConstructor(String.class);
        Object urlInstance = constructor.newInstance(latticeProperties.getHttpUrl());
        String url = (String) urlClass.getMethod("getValue").invoke(urlInstance);
        // URL url = new URL(latticeProperties.getHttpUrl());
        return new Lattice(url, latticeProperties.getChainId(), null);
    }

}
