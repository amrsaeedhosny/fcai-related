:-use_module(library(lists)).


play(BoardHeight, BoardWidth, DifficultyLevel):-
    retractall(boardHeight(_)),
    assert(boardHeight(BoardHeight)),
    retractall(boardWidth(_)),
    assert(boardWidth(BoardWidth)),
    retractall(boxesSize(_)),
    BoxesSize is BoardHeight * BoardWidth,
    assert(boxesSize(BoxesSize)),
    retractall(hLinesSize(_)),
    HLinesSize is BoardWidth * (BoardHeight + 1),
    assert(hLinesSize(HLinesSize)),
    retractall(vLinesSize(_)),
    VLinesSize is (BoardWidth + 1) * BoardHeight,
    assert(vLinesSize(VLinesSize)),
    initBoard([Boxes, HLines, VLines]),
    gameController([Boxes, HLines, VLines], BoardHeight, BoardWidth, DifficultyLevel).

/* GAME CONTROLLER */

gameController([Boxes, HLines, VLines], BoardHeight, BoardWidth, DifficultyLevel):-
    boxesSize(BoxesSize),
    printBoard([Boxes, HLines, VLines], BoardHeight, BoardWidth, 0),nl,
    write("Your Turn!"),nl,
    read(LineDirection),
    read(I),
    read(J),
    validMove([HLines, VLines], LineDirection, I, J),
    updateState([Boxes, HLines, VLines], LineDirection, I, J, StateA, "A"),
    checkState(StateA, BoxesSize),
    printBoard(StateA, BoardHeight, BoardWidth, 0),nl,
    write("Computer Turn!"),nl,
    alphabetamax(StateA, DifficultyLevel, -99, 99, Ret, StateB),
    checkState(StateB, BoxesSize),
    gameController(StateB, BoardHeight, BoardWidth, DifficultyLevel).

checkState([Boxes, HLines, VLines], BoxesSize):-
    isTerminal([Boxes, HLines, VLines], BoxesSize, 0),!,
    boardHeight(BoardHeight),
    boardWidth(BoardWidth),
    printBoard([Boxes, HLines, VLines], BoardHeight, BoardWidth, 0),
    char_code("A", A),
    char_code("B", B),
    count(Boxes, A, ACount),
    count(Boxes, B, BCount),
    win(ACount, BCount),
    break.

checkState(_, _):-!.

win(ACount, BCount):-
    ACount > BCount,
    write("You Won!"),
    write(ACount),
    write(" - "),
    write(BCount).

win(ACount, BCount):-
    ACount < BCount,
    write("Computer Won!"),
    write(BCount),
    write(" - "),
    write(ACount).

win(ACount, BCount):-
    ACount == BCount,
    write("Draw!"),
    write(ACount),
    write(" - "),
    write(BCount).

/* INITIALIZE THE BOARD */

initBoard([Boxes, HLines, VLines]):-!,
    boxesSize(BoxesSize),
    hLinesSize(HLinesSize),
    vLinesSize(VLinesSize),
    init(Boxes, BoxesSize, 0),
    init(HLines, HLinesSize, 0),
    init(VLines, VLinesSize, 0).

/* INITIALIZE LIST WITH EMPTY SLOTS */

init([], Size, Size):-!.
init([Element|Rest], Size, Index):-
    Element is " ",
    NewIndex is Index + 1,
    init(Rest, Size, NewIndex).

/* VALID MOVES */

validMove([HLines, _], "H", I, J):-!,
   boardHeight(BoardHeight),
   boardWidth(BoardWidth),
   I >= 0,
   I =< BoardHeight,
   J >= 0,
   J < BoardWidth,
   Index is I * BoardWidth + J,
   nth0(Index, HLines, HLine),
   HLine == 32.

validMove([_, VLines], "V", I, J):-!,
    boardHeight(BoardHeight),
    boardWidth(BoardWidth),
    I >= 0,
    I < BoardHeight,
    J >= 0,
    J =< BoardWidth,
    Index is I * (BoardWidth + 1) + J,
    nth0(Index, VLines, VLine),
    VLine == 32.


/* PRINTING THE BOARD */

printBoard([_, HLines, _], BoardHeight, BoardWidth, BoardHeight):-!,
    printHLines(HLines, BoardHeight, BoardWidth, BoardHeight, 0), nl.
printBoard([Boxes, HLines, VLines], BoardHeight, BoardWidth, I):-
    printRow([Boxes, HLines, VLines], BoardHeight, BoardWidth, I),
    NewI is I + 1,
    printBoard([Boxes, HLines, VLines], BoardHeight, BoardWidth, NewI).

printRow([Boxes, HLines, VLines], BoardHeight, BoardWidth, I):-
    printHLines(HLines, BoardHeight, BoardWidth, I, 0), nl,
    printVLinesAndBoxes([Boxes, VLines], BoardHeight, BoardWidth, I, 0),nl.

printHLines(_, _, BoardWidth, _, BoardWidth):-!,
    write(".").
printHLines(HLines, BoardHeight, BoardWidth, I, J):-
    write("."),
    Index is I * BoardWidth + J,
    nth0(Index, HLines, HLine),
    char_code(C, HLine),
    write(C),
    NewJ is J + 1,
    printHLines(HLines, BoardHeight, BoardWidth, I, NewJ).

printVLinesAndBoxes([_, VLines], _, BoardWidth, I, BoardWidth):-!,
    Index is I * (BoardWidth + 1) + BoardWidth,
    nth0(Index, VLines, VLine),
    char_code(C, VLine),
    write(C).
printVLinesAndBoxes([Boxes, VLines], BoardHeight, BoardWidth, I, J):-
    VIndex is I * (BoardWidth + 1) + J,
    BIndex is I * BoardWidth + J,
    nth0(VIndex, VLines, VLine),
    char_code(C1, VLine),
    write(C1),
    nth0(BIndex, Boxes, Box),
    char_code(C2, Box),
    write(C2),
    NewJ is J+1,
    printVLinesAndBoxes([Boxes, VLines], BoardHeight, BoardWidth, I, NewJ).

/* UPDATE BOARD */

replace(0, [_|T1], S, [S|T1]):-!.

replace(I, [H|T1], S, [H|T2]):-
    I1 is I-1,
    replace(I1, T1, S, T2).

updateState([Boxes, HLines, VLines], "H", I, J, [NewBoxes, NewHLines, VLines], Player):-!,
   boardWidth(BoardWidth),
   Index is I * BoardWidth + J,
   char_code('-', S),
   replace(Index, HLines, S, NewHLines),
   boxesSize(BoxesSize),
   updateBoxes([Boxes, NewHLines, VLines], [NewBoxes, NewHLines, VLines], BoxesSize, 0, Player).

updateState([Boxes, HLines, VLines], "V", I, J, [NewBoxes, HLines, NewVLines], Player):-!,
   boardWidth(BoardWidth),
   Index is I * (BoardWidth + 1) + J,
   char_code('|', S),
   replace(Index, VLines, S, NewVLines),
   boxesSize(BoxesSize),
   updateBoxes([Boxes, HLines, NewVLines], [NewBoxes, HLines, NewVLines], BoxesSize, 0, Player).

updateBoxes([Boxes, _, _], [Boxes, _, _], BoxesSize, BoxesSize, _):-!.

updateBoxes([Boxes, HLines, VLines], [NewBoxes, HLines, VLines], BoxesSize, Index, Player):-
    boardWidth(BoardWidth),
    I is div(Index, BoardWidth),
    J is mod(Index, BoardWidth),
    LeftIndex is I * (BoardWidth + 1) + J,
    TopIndex is Index,
    RightIndex is LeftIndex + 1,
    BottomIndex is TopIndex + BoardWidth,
    char_code(' ', S),
    char_code('|', V),
    char_code('-', H),
    char_code(Player, P),
    nth0(Index, Boxes, Box),
    Box == S,
    nth0(LeftIndex, VLines, Left),
    Left == V,
    nth0(TopIndex, HLines, Top),
    Top == H,
    nth0(RightIndex, VLines, Right),
    Right == V,
    nth0(BottomIndex, HLines, Bottom),
    Bottom == H,
    replace(Index, Boxes, P, NewBoxesTemp),
    NewIndex is Index + 1,
    updateBoxes([NewBoxesTemp, HLines, VLines], [NewBoxes, HLines, VLines], BoxesSize, NewIndex, Player).

updateBoxes([Boxes, HLines, VLines], [NewBoxes, HLines, VLines], BoxesSize, Index, Player):-
    NewIndex is Index + 1,
    updateBoxes([Boxes, HLines, VLines], [NewBoxes, HLines, VLines], BoxesSize, NewIndex, Player).


/* ALPHABETA ALGORITHM */


alphabetamax(State, Depth, _, _, Ret, _):-
     boxesSize(BoxesSize),
     (isTerminal(State, BoxesSize, 0); Depth == 0),!,
     getHeuristic(State, Ret).

alphabetamax(State, Depth, Alpha, Beta, Ret, Next):-
    getChildren(State, Children, "B"),
    selectChildmax(Children, Depth, Alpha, Beta, Ret, _, Next).

alphabetamin(State, Depth, _, _, Ret, _):-
    boxesSize(BoxesSize),
    (isTerminal(State, BoxesSize, 0); Depth == 0),!,
    getHeuristic(State, Ret).

alphabetamin(State,Depth,Alpha,Beta,Ret,Next):-
    getChildren(State, Children, "A"),
    selectChildmin(Children, Depth, Alpha, Beta, Ret, _, Next).


min(A, B, A, VA, _, VA):-
    B >= A,!.
min(_, B, B, _, VB, VB).

max(A, B, A, VA, _, VA):-
    A >= B,!.
max(_, B, B, _, VB, VB).


selectChildmax(_, _, A, B, A, _, _):-
     B =< A,!.

selectChildmax([], _, Alpha, _, Alpha, BestTillNow, BestTillNow).

selectChildmax([H|T], Depth, Alpha, Beta, Ret, BestTillNow, SelectChild):-
    NDepth is Depth - 1,
    alphabetamin(H, NDepth, Alpha, Beta, NRet,_),
    max(Alpha, NRet, UpdetedAlpha, BestTillNow, H, NewBest),
    selectChildmax(T, Depth, UpdetedAlpha, Beta, Ret, NewBest, SelectChild).


selectChildmin(_, _, A, B, B, _, _):-
     B =< A,!.

selectChildmin([], _, _, Beta, Beta, BestTillNow, BestTillNow).

selectChildmin([H|T], Depth, Alpha, Beta, Ret, BestTillNow, SelectChild):-
    NDepth is Depth - 1,
    alphabetamax(H, NDepth, Alpha, Beta, NRet, _),
    min(Beta, NRet, UpdetedBeta, BestTillNow, H, NewBest),
    selectChildmin(T, Depth, Alpha, UpdetedBeta, Ret, NewBest, SelectChild).


/* GET THE CHILDREN OF A STATE */

getChildren([Boxes, HLines, VLines], Children, Player):-
    hLinesSize(HLinesSize),
    vLinesSize(VLinesSize),
    getHChildren([Boxes, HLines, VLines], Player, HLinesSize, 0, HChildren),
    getVChildren([Boxes, HLines, VLines], Player, VLinesSize, 0, VChildren),
    append(HChildren, VChildren, Children).


getHChildren(_, _, HLinesSize, HLinesSize, []):-!.

getHChildren([Boxes, HLines, VLines], Player, HLinesSize, HLinesCounter, [HChild|RestHChildren]):-
    boardWidth(BoardWidth),
    I is div(HLinesCounter, BoardWidth),
    J is mod(HLinesCounter, BoardWidth),
    validMove([HLines, _], "H", I, J),
    updateState([Boxes, HLines, VLines], "H", I, J, HChild, Player),
    NewHLinesCounter is HLinesCounter + 1,
    getHChildren([Boxes, HLines, VLines], Player, HLinesSize, NewHLinesCounter, RestHChildren).

getHChildren([Boxes, HLines, VLines], Player, HLinesSize, HLinesCounter, HChildren):-
    NewHLinesCounter is HLinesCounter + 1,
    getHChildren([Boxes, HLines, VLines], Player, HLinesSize, NewHLinesCounter, HChildren).



getVChildren(_, _, VLinesSize, VLinesSize, []):-!.

getVChildren([Boxes, HLines, VLines], Player, VLinesSize, VLinesCounter, [VChild|RestVChildren]):-
    boardWidth(BoardWidth),
    VLinesWidth is BoardWidth +1,
    I is div(VLinesCounter, VLinesWidth),
    J is mod(VLinesCounter, VLinesWidth),
    validMove([_, VLines], "V", I, J),
    updateState([Boxes, HLines, VLines], "V", I, J, VChild, Player),
    NewVLinesCounter is VLinesCounter + 1,
    getVChildren([Boxes, HLines, VLines], Player, VLinesSize, NewVLinesCounter, RestVChildren).

getVChildren([Boxes, HLines, VLines], Player, VLinesSize, VLinesCounter, VChildren):-
    NewVLinesCounter is VLinesCounter + 1,
    getVChildren([Boxes, HLines, VLines], Player, VLinesSize, NewVLinesCounter, VChildren).


/* CHECK IF A STATE IS A TERMINAL ONE */

isTerminal(_, BoxesSize, BoxesSize).
isTerminal([Boxes, _, _], BoxesSize, Index):-
    nth0(Index, Boxes, Box),
    Box =\= 32,
    NewIndex is Index + 1,
    isTerminal([Boxes, _, _], BoxesSize, NewIndex).


/* HEURISTIC FUNCTION */

getHeuristic([Boxes, _, _], Value):-
    char_code("A", A),
    char_code("B", B),
    count(Boxes, A, ACount),
    count(Boxes, B, BCount),
    Value is BCount - ACount.

count([], _, 0).

count([Player|RestOfBoxes], Player, Count):-
    count(RestOfBoxes, Player, TempCount),
    Count is TempCount +1.

count([_|RestOfBoxes], Player, Count):-
    count(RestOfBoxes, Player, Count).




