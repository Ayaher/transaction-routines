package br.com.bbex.transactions.startup;

import org.springframework.stereotype.Component;

import br.com.bbex.transactions.repository.OperationTypeRepository;
import br.com.bbex.transactions.model.OperationType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Component
public class DataLoader implements ApplicationRunner {

    private OperationTypeRepository opRepository;

    @Autowired
    public DataLoader(OperationTypeRepository opRepository) {
        this.opRepository = opRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Criando dados iniciais...");
        long count = opRepository.count();
        if (count == 0) {
            opRepository.save(new OperationType("COMPRA À VISTA", true));
            opRepository.save(new OperationType("COMPRA PARCELADA", true));
            opRepository.save(new OperationType("SAQUE", true));
            opRepository.save(new OperationType("PAGAMENTO", false));
        } else {
            System.out.println("Já existem tipos de pagamento no banco.");
        }
    }

}