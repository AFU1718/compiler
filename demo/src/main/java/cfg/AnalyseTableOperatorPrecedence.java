package cfg;

import cfg.OperatorPrecedence.Precedence;

import java.util.Arrays;
import java.util.Map;

public class AnalyseTableOperatorPrecedence {
    private Precedence[][] table;
    private Map<Integer, Integer> TMapping;

    public AnalyseTableOperatorPrecedence(Precedence[][] table, Map<Integer, Integer> TMapping) {
        this.table = table;
        this.TMapping = TMapping;
    }

    public Precedence[][] getTable() {
        return table;
    }

    public void setTable(Precedence[][] table) {
        this.table = table;
    }

    public Map<Integer, Integer> getTMapping() {
        return TMapping;
    }

    public void setTMapping(Map<Integer, Integer> TMapping) {
        this.TMapping = TMapping;
    }

    @Override
    public String toString() {
        return "AnalyseTableOperatorPrecedence{" +
                "table=" + Arrays.toString(table) +
                ", TMapping=" + TMapping +
                '}';
    }
}
