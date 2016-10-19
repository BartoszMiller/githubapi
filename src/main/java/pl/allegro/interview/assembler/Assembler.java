package pl.allegro.interview.assembler;

public interface Assembler<T1, T2> {

    T2 toResource(T1 t1);
}
