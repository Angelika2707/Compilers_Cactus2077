package main.ast;


import java.util.ArrayList;
import java.util.List;

public class RecordDeclaration implements Declaration {
    private final List<VariableDeclaration> fields;

    public RecordDeclaration(List<VariableDeclaration> fields) {
        this.fields = fields != null ? fields : new ArrayList<>();
    }

    public void addField(VariableDeclaration field) {
        fields.add(field);
    }

    public List<VariableDeclaration> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "RecordType{" + fields + '}';
    }
}

