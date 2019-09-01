package dlv;

import java.util.ArrayList;
import java.util.List;
import core.PawnDLV;
import core.Position;

public  class Intelligence{

private List<PawnDLV> blackPawnsDLV= new ArrayList<PawnDLV>();
private List<PawnDLV> whitePawnsDLV= new ArrayList<PawnDLV>();
private List<Position> emptyPositions= new ArrayList<Position>();
public Intelligence(){
}
public boolean playerCanMove(){
List<PawnDLV> adjWhites= new ArrayList<PawnDLV>();
List<PawnDLV> pawnsCanMove= new ArrayList<PawnDLV>();

	// ---- START - startProgram ---- 
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Creation program JDLV module.");
it.unical.mat.wrapper.DLVInputProgram _JDLV_PROGRAM_program=new it.unical.mat.wrapper.DLVInputProgramImpl();
_JDLV_PROGRAM_program.cleanText();
java.lang.StringBuffer _JDLV_PROGRAM_BUFFER_program=new java.lang.StringBuffer();
it.unical.mat.wrapper.DLVInvocation _JDLV_INVOCATION_program;

	// ---- END - startProgram ---- 

	// ---- START - addInMapping ---- 
_JDLV_PROGRAM_program.addText(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(blackPawnsDLV,"blacks"));
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add in-mapping 'blackPawnsDLV::blacks' in module program:"+ it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(blackPawnsDLV,"blacks"), 0));
_JDLV_PROGRAM_program.addText(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(whitePawnsDLV,"whites"));
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add in-mapping 'whitePawnsDLV::whites' in module program:"+ it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(whitePawnsDLV,"whites"), 0));
_JDLV_PROGRAM_program.addText(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(emptyPositions,"emptyPos"));
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add in-mapping 'emptyPositions::emptyPos' in module program:"+ it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(emptyPositions,"emptyPos"), 0));

	// ---- END - addInMapping ---- 
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add out-mapping 'pawnsCanMove::whitesMove' in module program.");
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add out-mapping 'adjWhites::adjW' in module program.");

	// ---- START - prepareJDLVCall ---- 
try{

_JDLV_INVOCATION_program=it.unical.mat.wrapper.DLVWrapper.getInstance().createInvocation(it.unical.mat.jdlv.util.JdlvProperties.getInstance().getDlvExecutablePath());
_JDLV_PROGRAM_program.addText(_JDLV_PROGRAM_BUFFER_program.toString());
_JDLV_PROGRAM_program.addText("whitesMove(X, Y, C) :- whites(X, Y, C), emptyPos(K, Z), K = X - 1, Z = Y + 1."+'\n');
_JDLV_PROGRAM_program.addText("whitesMove(X, Y, C) :- whites(X, Y, C), emptyPos(K, Z), K = X - 1, Z = Y - 1."+'\n');
_JDLV_PROGRAM_program.addText("whitesMove(X, Y, C) :- whites(X, Y, C), emptyPos(K, Z), K = X + 1, Z = Y + 1, C = 1."+'\n');
_JDLV_PROGRAM_program.addText("whitesMove(X, Y, C) :- whites(X, Y, C), emptyPos(K, Z), K = X + 1, Z = Y - 1, C = 1."+'\n');
_JDLV_PROGRAM_program.addText("adjW(X2, Y2, C2) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X1 = X2 + 1, Y1 = Y2 + 1, emptyPos(K, Z), K = X2 + 2, Z = Y2 + 2, C2 = 1."+'\n');
_JDLV_PROGRAM_program.addText("adjW(X2, Y2, C2) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X1 = X2 + 1, Y1 = Y2 - 1, emptyPos(K, Z), K = X2 + 2, Z = Y2 - 2, C2 = 1."+'\n');
_JDLV_PROGRAM_program.addText("adjW(X2, Y2, C2) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X1 = X2 - 1, Y1 = Y2 + 1, emptyPos(K, Z), K = X2 - 2, Z = Y2 + 2, C2 = 1."+'\n');
_JDLV_PROGRAM_program.addText("adjW(X2, Y2, C2) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X1 = X2 - 1, Y1 = Y2 - 1, emptyPos(K, Z), K = X2 - 2, Z = Y2 - 2, C2 = 1."+'\n');
_JDLV_PROGRAM_program.addText("adjW(X2, Y2, C2) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X1 = X2 - 1, Y1 = Y2 + 1, emptyPos(K, Z), K = X2 - 2, Z = Y2 + 2, C1 = 0, C2 = 0."+'\n');
_JDLV_PROGRAM_program.addText("adjW(X2, Y2, C2) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X1 = X2 - 1, Y1 = Y2 - 1, emptyPos(K, Z), K = X2 - 2, Z = Y2 - 2, C1 = 0, C2 = 0."+'\n');
_JDLV_PROGRAM_program.getFiles().clear();
_JDLV_INVOCATION_program.setNumberOfModels(1);
_JDLV_PROGRAM_BUFFER_program.append("");
_JDLV_INVOCATION_program.setInputProgram(_JDLV_PROGRAM_program);
it.unical.mat.wrapper.ModelBufferedHandler _BUFFERED_HANDLER_program=new it.unical.mat.wrapper.ModelBufferedHandler(_JDLV_INVOCATION_program);
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Start execution program program: executablePath='"+it.unical.mat.jdlv.util.JdlvProperties.getInstance().getDlvExecutablePath()+"', options='"+_JDLV_INVOCATION_program.getOptionsString()+"'"+'\n'+"Code execution: "+it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(_JDLV_INVOCATION_program.getInputProgram().getCompleteText(),0));
_JDLV_INVOCATION_program.run();
while(_BUFFERED_HANDLER_program.hasMoreModels()){
it.unical.mat.wrapper.Model _temporary_JDLV_MODELprogram=_BUFFERED_HANDLER_program.nextModel();
it.unical.mat.jdlv.program.TypeSolver.loadPredicate(pawnsCanMove, "whitesMove",_temporary_JDLV_MODELprogram,PawnDLV.class);
it.unical.mat.jdlv.program.TypeSolver.loadPredicate(adjWhites, "adjW",_temporary_JDLV_MODELprogram,PawnDLV.class);
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Process new answer_set"+ '\n' + "Results:"+ '\n'+ "pawnsCanMove " + pawnsCanMove.size() + " elements"+ '\n' + it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyObject(pawnsCanMove,0)+ "adjWhites " + adjWhites.size() + " elements"+ '\n' + it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyObject(adjWhites,0));
}
if(_JDLV_INVOCATION_program.haveModel()==false){
System.out.println( "no_answerset" );
}
if(!_JDLV_INVOCATION_program.getErrors().isEmpty()){
throw new java.lang.RuntimeException(_JDLV_INVOCATION_program.getErrors().get(0).getText());
}
}
catch(java.lang.Throwable _JDLV_EXCEPTION_program){
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logErrorMessage(_JDLV_EXCEPTION_program.getMessage());
System.err.println( "error" );
}
_JDLV_PROGRAM_program.cleanText();

	// ---- END - prepareJDLVCall ---- 
return !(pawnsCanMove.isEmpty()&&adjWhites.isEmpty());
}
public List<PawnDLV> getResult(){
List<PawnDLV> result= new ArrayList<PawnDLV>();

	// ---- START - startProgram ---- 
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Creation program JDLV module.");
it.unical.mat.wrapper.DLVInputProgram _JDLV_PROGRAM_program=new it.unical.mat.wrapper.DLVInputProgramImpl();
_JDLV_PROGRAM_program.cleanText();
java.lang.StringBuffer _JDLV_PROGRAM_BUFFER_program=new java.lang.StringBuffer();
it.unical.mat.wrapper.DLVInvocation _JDLV_INVOCATION_program;

	// ---- END - startProgram ---- 

	// ---- START - addInMapping ---- 
_JDLV_PROGRAM_program.addText(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(blackPawnsDLV,"blacks"));
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add in-mapping 'blackPawnsDLV::blacks' in module program:"+ it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(blackPawnsDLV,"blacks"), 0));
_JDLV_PROGRAM_program.addText(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(whitePawnsDLV,"whites"));
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add in-mapping 'whitePawnsDLV::whites' in module program:"+ it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(whitePawnsDLV,"whites"), 0));
_JDLV_PROGRAM_program.addText(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(emptyPositions,"empty"));
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add in-mapping 'emptyPositions::empty' in module program:"+ it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(it.unical.mat.jdlv.program.TypeSolver.getNameTranslation(emptyPositions,"empty"), 0));

	// ---- END - addInMapping ---- 
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Add out-mapping 'result::inRes' in module program.");

	// ---- START - prepareJDLVCall ---- 
try{

_JDLV_INVOCATION_program=it.unical.mat.wrapper.DLVWrapper.getInstance().createInvocation(it.unical.mat.jdlv.util.JdlvProperties.getInstance().getDlvExecutablePath());
_JDLV_PROGRAM_program.addText(_JDLV_PROGRAM_BUFFER_program.toString());
_JDLV_PROGRAM_program.addText("board(7)."+'\n');
_JDLV_PROGRAM_program.addText("outRes(X, Y, C) v inRes(X, Y, C) :- blacks(X, Y, C)."+'\n');
_JDLV_PROGRAM_program.addText("canTake(X1, Y1, C1) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X2 = X1 + 1, Y2 = Y1 + 1, empty(K, Z), K = X2 + 1, Z = Y2 + 1, C1 = 0, C2 = 0."+'\n');
_JDLV_PROGRAM_program.addText("canTake(X1, Y1, C1) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X2 = X1 + 1, Y2 = Y1 - 1, empty(K, Z), K = X2 + 1, Z = Y2 - 1, C1 = 0, C2 = 0."+'\n');
_JDLV_PROGRAM_program.addText("canTake(X1, Y1, C1) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X2 = X1 + 1, Y2 = Y1 + 1, empty(K, Z), K = X2 + 1, Z = Y2 + 1, C1 = 1."+'\n');
_JDLV_PROGRAM_program.addText("canTake(X1, Y1, C1) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X2 = X1 + 1, Y2 = Y1 - 1, empty(K, Z), K = X2 + 1, Z = Y2 - 1, C1 = 1."+'\n');
_JDLV_PROGRAM_program.addText("canTake(X1, Y1, C1) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X2 = X1 - 1, Y2 = Y1 + 1, empty(K, Z), K = X2 - 1, Z = Y2 + 1, C1 = 1."+'\n');
_JDLV_PROGRAM_program.addText("canTake(X1, Y1, C1) :- blacks(X1, Y1, C1), whites(X2, Y2, C2), X2 = X1 - 1, Y2 = Y1 - 1, empty(K, Z), K = X2 - 1, Z = Y2 - 1, C1 = 1."+'\n');
_JDLV_PROGRAM_program.addText("tmp(X, Y) :- canTake(X, Y, _)."+'\n');
_JDLV_PROGRAM_program.addText("canJump(X, Y, C) :- blacks(X, Y, C), empty(K, Z), K = X + 1, Z = Y + 1, not canTake(X, Y, C)."+'\n');
_JDLV_PROGRAM_program.addText("canJump(X, Y, C) :- blacks(X, Y, C), empty(K, Z), K = X + 1, Z = Y - 1, not canTake(X, Y, C)."+'\n');
_JDLV_PROGRAM_program.addText("canJump(X, Y, C) :- blacks(X, Y, C), empty(K, Z), K = X - 1, Z = Y + 1, C = 1, not tmp(X, Y)."+'\n');
_JDLV_PROGRAM_program.addText("canJump(X, Y, C) :- blacks(X, Y, C), empty(K, Z), K = X - 1, Z = Y - 1, C = 1, not tmp(X, Y)."+'\n');
_JDLV_PROGRAM_program.addText("canJump(X, Y, C) :- blacks(X, Y, C), empty(K, Z), K = X + 1, Z = Y + 1, C = 1, not tmp(X, Y)."+'\n');
_JDLV_PROGRAM_program.addText("canJump(X, Y, C) :- blacks(X, Y, C), empty(K, Z), K = X + 1, Z = Y - 1, C = 1, not tmp(X, Y)."+'\n');
_JDLV_PROGRAM_program.addText("allJump(X, Y, C, K, Z) :- canJump(X, Y, C), empty(K, Z), K = X + 1, Z = Y + 1."+'\n');
_JDLV_PROGRAM_program.addText("allJump(X, Y, C, K, Z) :- canJump(X, Y, C), empty(K, Z), K = X + 1, Z = Y - 1."+'\n');
_JDLV_PROGRAM_program.addText("allJump(X, Y, 1, K, Z) :- canJump(X, Y, 1), empty(K, Z), K = X - 1, Z = Y + 1."+'\n');
_JDLV_PROGRAM_program.addText("allJump(X, Y, 1, K, Z) :- canJump(X, Y, 1), empty(K, Z), K = X - 1, Z = Y - 1."+'\n');
_JDLV_PROGRAM_program.addText(":- #count{X,Y : inRes(X, Y, _)} = 0."+'\n');
_JDLV_PROGRAM_program.addText(":- inRes(X, Y, C), not canJump(X, Y, C), #count{K,Z : canTake(K, Z, _)} = 0."+'\n');
_JDLV_PROGRAM_program.addText(":~ canJump(X, Y, 1), inRes(X, Y, _). [1:5]"+'\n');
_JDLV_PROGRAM_program.addText(":~ canJump(X, Y, _), outRes(X, Y, _), #count{K,Z : canTake(K, Z, _)} = 0. [1:4]"+'\n');
_JDLV_PROGRAM_program.addText(":~ canJump(X, Y, _), inRes(X, Y, _), board(B), W = B - X. [W:4]"+'\n');
_JDLV_PROGRAM_program.addText(":~ inRes(X, Y, C1), allJump(X, Y, C1, K, Z), whites(F, G, C2), C1 <= C2, #absdiff(K, F, D1), #absdiff(Z, G, D2), D1 = 1, D2 = 1. [1:3]"+'\n');
_JDLV_PROGRAM_program.addText(":~ canJump(X, Y, _), inRes(X, Y, _). [1:2]"+'\n');
_JDLV_PROGRAM_program.addText(":~ canTake(X, Y, _), outRes(X, Y, _). [1:1]"+'\n');
_JDLV_PROGRAM_program.addText(":~ inRes(X, Y, C), not canJump(X, Y, C). [1:1]"+'\n');
_JDLV_PROGRAM_program.getFiles().clear();
_JDLV_INVOCATION_program.setNumberOfModels(1);
_JDLV_PROGRAM_BUFFER_program.append("");
_JDLV_INVOCATION_program.setInputProgram(_JDLV_PROGRAM_program);
it.unical.mat.wrapper.ModelBufferedHandler _BUFFERED_HANDLER_program=new it.unical.mat.wrapper.ModelBufferedHandler(_JDLV_INVOCATION_program);
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Start execution program program: executablePath='"+it.unical.mat.jdlv.util.JdlvProperties.getInstance().getDlvExecutablePath()+"', options='"+_JDLV_INVOCATION_program.getOptionsString()+"'"+'\n'+"Code execution: "+it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyCode(_JDLV_INVOCATION_program.getInputProgram().getCompleteText(),0));
_JDLV_INVOCATION_program.run();
while(_BUFFERED_HANDLER_program.hasMoreModels()){
it.unical.mat.wrapper.Model _temporary_JDLV_MODELprogram=_BUFFERED_HANDLER_program.nextModel();
it.unical.mat.jdlv.program.TypeSolver.loadPredicate(result, "inRes",_temporary_JDLV_MODELprogram,PawnDLV.class);
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logInfoMessage("Process new answer_set"+ '\n' + "Results:"+ '\n'+ "result " + result.size() + " elements"+ '\n' + it.unical.mat.jdlv.program.JDLV_Logger.getInstance().getPrettyObject(result,0));
}
if(_JDLV_INVOCATION_program.haveModel()==false){
System.out.println( "no_answerset" );
}
if(!_JDLV_INVOCATION_program.getErrors().isEmpty()){
throw new java.lang.RuntimeException(_JDLV_INVOCATION_program.getErrors().get(0).getText());
}
}
catch(java.lang.Throwable _JDLV_EXCEPTION_program){
it.unical.mat.jdlv.program.JDLV_Logger.getInstance().logErrorMessage(_JDLV_EXCEPTION_program.getMessage());
System.err.println( "error" );
}
_JDLV_PROGRAM_program.cleanText();

	// ---- END - prepareJDLVCall ---- 
return result;
}
public  void  setWhitePawnsDLV(List<PawnDLV> whitePawnsDLV){
 this .whitePawnsDLV=whitePawnsDLV;
}
public  void  setBlackPawnsDLV(List<PawnDLV> blackPawnsDLV){
 this .blackPawnsDLV=blackPawnsDLV;
}
public  void  setEmptyPositions(List<Position> emptyPositions){
 this .emptyPositions=emptyPositions;
}
}

