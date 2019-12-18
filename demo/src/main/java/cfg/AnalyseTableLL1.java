package cfg;

import java.util.Map;

public class AnalyseTableLL1 {
    private Rule[][] table;
    private Map<String, Integer> NTMapping;
    private Map<Integer, Integer> TMapping;

    public AnalyseTableLL1(Rule[][] table, Map<String, Integer> NTMapping, Map<Integer, Integer> TMapping) {
        this.table = table;
        this.NTMapping = NTMapping;
        this.TMapping = TMapping;
    }

    public Rule[][] getTable() {
        return table;
    }

    public void setTable(Rule[][] table) {
        this.table = table;
    }

    public Map<String, Integer> getNTMapping() {
        return NTMapping;
    }

    public void setNTMapping(Map<String, Integer> NTMapping) {
        this.NTMapping = NTMapping;
    }

    public Map<Integer, Integer> getTMapping() {
        return TMapping;
    }

    public void setTMapping(Map<Integer, Integer> TMapping) {
        this.TMapping = TMapping;
    }
}
