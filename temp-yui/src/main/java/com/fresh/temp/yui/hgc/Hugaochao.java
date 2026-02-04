package com.fresh.temp.yui.hgc;

public class Hugaochao {


    public static void main(String[] argv) {
        //胡高超，长高 1 米，体重增加 20 斤，获取大量知识后 take off.
        Hugaochao.of().with(Height.of(1, HeightUnit.MI)::addTo).with(Weight.of(10, WeightUnit.KG)::addTo).with(Knowledge.of(KnowledgeUnit.LargeAmount)::addTo).takeOff();
    }


    Hugaochao with(Adjuster adjuster) {
        return adjuster.adjustInto(this);
    }

    void takeOff() {}

    static Hugaochao of() {
        return new Hugaochao();
    }
}


interface Adjuster {
    Hugaochao adjustInto(Hugaochao hugaochao);
}


enum HeightUnit {
    MI(1, "米");

    private int value;
    private String desc;
    HeightUnit(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
class Height /*implements Adjuster*/ {
    private int num;
    private HeightUnit unit;
    Height(int num, HeightUnit unit) {
        this.num = num;
        this.unit = unit;
    }

    static Height of(int num, HeightUnit unit) {
        return new Height(num, unit);
    }

    public Hugaochao addTo(Hugaochao hugaochao) {
        //todo
        return hugaochao;
    }
}

enum WeightUnit {
    KG(1, "千克");

    private int value;
    private String desc;
    WeightUnit(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
class Weight /*implements Adjuster*/ {
    private int num;
    private WeightUnit unit;

    Weight(int num, WeightUnit unit) {
        this.num = num;
        this.unit = unit;
    }

    static Weight of(int num, WeightUnit unit) {
        return new Weight(num, unit);
    }

    public Hugaochao addTo(Hugaochao hugaochao) {
        //todo
        return hugaochao;
    }
}

enum KnowledgeUnit {
    LargeAmount
}
class Knowledge /*implements Adjuster*/ {
    Knowledge(KnowledgeUnit unit) {}
    static Knowledge of(KnowledgeUnit unit) {
        return new Knowledge(unit);
    }
    public Hugaochao addTo(Hugaochao hugaochao) {
        //todo
        return hugaochao;
    }
}
