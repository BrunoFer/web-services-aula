#coding: utf-8
import urllib
import urllib2
import re
import socket
import exceptions
import redis

iplocal = '10.3.1.50'
porta = 6666

db = redis.Redis('localhost')

while True:

	socketServer = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
	try:
		socketServer.bind((iplocal,porta))
	except:
		print "\nNão foi possível usar o endereço! Erro de socket...\n"

	cep, dadosCliente = socketServer.recvfrom(1024)
	print
	print 'Nova Requisição ======================================'
	print dadosCliente
	
	if (db.get(cep)==None):
		host = "http://cep.correiocontrol.com.br/{}.json".format(cep)
		res = urllib2.Request(host)
		con = urllib2.urlopen(res)
		resposta = con.read()
		db.set(cep,resposta)
		print "Buscando da API"
	else:
		print "Está no cache"
		resposta = db.get(cep)

	socketServer.sendto(resposta,(dadosCliente[0],dadosCliente[1]))
	socketServer.close()


