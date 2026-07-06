package com.mycompany.s2501.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class InventarioJogador {

    private static final int LIMITE_POR_ITEM = 3;

    private static final Map<Integer, Item> itens = new LinkedHashMap<>();
    private static final Map<Integer, Integer> quantidades = new LinkedHashMap<>();

    private InventarioJogador() {
    }

    public static boolean adicionarItem(Item item) {
        int id = item.getId();
        int quantidadeAtual = getQuantidade(id);

        if (quantidadeAtual >= LIMITE_POR_ITEM) {
            return false;
        }

        itens.putIfAbsent(id, item);
        quantidades.put(id, quantidadeAtual + 1);

        return true;
    }

    public static int getQuantidade(int id) {
        return quantidades.getOrDefault(id, 0);
    }

    public static boolean removerItem(int id) {
        int quantidadeAtual = getQuantidade(id);

        if (quantidadeAtual <= 0) {
            return false;
        }

        if (quantidadeAtual == 1) {
            quantidades.remove(id);
            itens.remove(id);
        } else {
            quantidades.put(id, quantidadeAtual - 1);
        }

        return true;
    }

    public static int getQuantidadeDisponivel(int id) {
        return LIMITE_POR_ITEM - getQuantidade(id);
    }

    public static int getLimitePorItem() {
        return LIMITE_POR_ITEM;
    }

    public static Collection<Item> getItens() {
        return Collections.unmodifiableCollection(itens.values());
    }
    
    public static void limpar() {
        itens.clear();
        quantidades.clear();
    }

    public static boolean temItem(int id) {
        return itens.containsKey(id);
    }

    public static void adicionarFragmentoChave(int numeroFragmento) {
        int idFragmento = 9000 + numeroFragmento;

        if (temItem(idFragmento)) {
            return;
        }

        Item fragmento = new Item(
                idFragmento,
                "Fragmento de Chave " + numeroFragmento + "/4",
                "Um pedaço da chave do quarto trancado.",
                0,
                "/com/mycompany/s2501/jogotlp/f1.png"
        );

        itens.put(idFragmento, fragmento);
        quantidades.put(idFragmento, 1);
    }
}
