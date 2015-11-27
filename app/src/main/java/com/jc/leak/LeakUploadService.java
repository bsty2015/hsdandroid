//package com.jc.leak;
//
//import com.squareup.leakcanary.AnalysisResult;
//import com.squareup.leakcanary.DisplayLeakService;
//import com.squareup.leakcanary.HeapDump;
//
///**
// * Created by lrh on 5/11/15.
// */
//public class LeakUploadService extends DisplayLeakService {
//
//    @Override
//    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
//        if (!result.leakFound || result.excludedLeak) {
//            return;
//        }
//
//    }
//}
