//package com.mika.reflect;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import javax.annotation.processing.Processor;
//
///**
// * @Author: mika
// * @Time: 2018-11-15 11:07
// * @Description:
// */
//public class CompileOptions {
//
//    final List<? extends Processor> processors;
//
//    public CompileOptions() {
//        this(
//                Collections.emptyList()
//        );
//    }
//
//    private CompileOptions(List<? extends Processor> processors) {
//        this.processors = processors;
//    }
//
//    public CompileOptions processors(Processor... processors) {
//        return processors(Arrays.asList(processors));
//    }
//
//    public CompileOptions processors(List<? extends Processor> processors) {
//        return new CompileOptions(processors);
//    }
//}
