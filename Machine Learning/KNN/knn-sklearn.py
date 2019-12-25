from idlelib.EditorWindow import get_accelerator

import numpy as np
import operator

def read_data(file_path):
    with open(file_path) as f:
        data = [i[:-1].split(',') for i in f]

        x = [list(map(float,el[:-1])) for el in data]
        y = [el[-1] for el in data]
    return x,y

def euclid_dist(example1, example2):
    len = example1.__len__()
    sum = 0
    for i in range(len):
        sum += (example1[i] - example2[i]) ** 2
    return np.sqrt(sum)

def accuracy(Y_Actual, Y_calculated):
    correctY = sum([1 if el1 == el2 else 0 for el1,el2 in zip(Y_Actual,Y_calculated)])
    ratio = (correctY*1.0)/len(Y_Actual)
    return ratio,correctY


def best_class(knn_classes):
    classes_dict = {}
    for c in knn_classes:
        if c in classes_dict:
            classes_dict[c] += 1
        else:
            classes_dict[c] = 1
    sorted_classes = sorted(classes_dict.items(), key=operator.itemgetter(1), reverse=True)
    tie = 0

    max_occur = sorted_classes[0][1]
    for c in sorted_classes:
        if c[1] == max_occur:
            tie += 1
        else:
            break
    if tie > 1:
        for c in Y:
            for i in range(tie):
                if c == sorted_classes[i][0]:
                    print("tie",c)
                    return c

    return sorted_classes[0][0]


def get_KNN(X,Y,test_point,k):
    # distances = [(sum((test_point - x)**2))**0.5 for x in X] #euclid_dist
    distances = [(euclid_dist(x,test_point),y) for x,y in zip(X,Y)]
    dis = sorted(distances)
    knn_dis = dis[:k]

    knn_classes = [c[1] for c in knn_dis]
    print(knn_classes)
    return best_class(knn_classes)

def getBestK(X, Y, X_Test, Y_Test, min_k, max_k):
    best_k = min_k
    mx_accuracy = -1
    for k in range(min_k,max_k):
        predicted_classes = []
        for i in range(len(X_Test)):
            predicted_classes += [get_KNN(X, Y, X_Test[i], k)]
        acc,numOfCorrect = accuracy(Y_Test, predicted_classes)
        if(acc > mx_accuracy):
            mx_accuracy = acc
            best_k = k
        print("k=",k,"then accuracy =", acc,"correct =",numOfCorrect)
    return best_k,mx_accuracy

X,Y = read_data('TrainData.txt')
X_Test,Y_Test = read_data('TestData.txt')
print(X)
print(Y)

print(X_Test)
print(Y_Test)

print(getBestK(X[:-1],Y[:-1],X_Test[:-1],Y_Test[:-1],3,4))
# print(X[:-1].__len__())
# for test,res in zip(X_Test[:-1],Y_Test[:-1]):
#     print(get_KNN(np.array(X[:-1]),Y,np.array(test),1), res)
