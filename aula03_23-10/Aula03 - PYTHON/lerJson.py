# encoding: utf-8

import json
import requests

host = "http://10.3.1.51/Turma.json"
res = requests.get(host)

stringJson = json.loads(res.content)
#print stringJson

print "\nCurso: ",stringJson['curso']

i=0
print "Turmas: "
while i<len(stringJson['turmas']):
	print "\n\nBancada: ",stringJson['turmas'][i]['turma']
	print "Período: ",stringJson['turmas'][i]['periodo']
	k=0
	print "\n\tAlunos: "
	while k<len(stringJson['turmas'][i]['alunos']):
		print "Nome aluno: ",stringJson['turmas'][i]['alunos'][k]['nome']
		print "Nota aluno: ",stringJson['turmas'][i]['alunos'][k]['nota']
		print "Id aluno: ",stringJson['turmas'][i]['alunos'][k]['id']
		k+=1
	i+=1

print
