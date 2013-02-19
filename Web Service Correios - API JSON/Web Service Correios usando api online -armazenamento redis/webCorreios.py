#coding: utf-8

import socket
import exceptions
import json

iplocal='10.3.1.51'
ipservidor='10.3.1.50'
porta=6666
exit=False

def verificaCEP(cep):
	if len(cep)!=8:
		return False
	if cep.isdigit()==False:
		return False
	return True
	

while exit==False:

	cep = raw_input('\nDigite o cep (Digite s para sair): ')

	if cep=='s':
		exit=True
	else:
		if verificaCEP(cep)==False:
			print 'CEP Inválido!! Digite novamente...'
		else:
			socketCliente = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

			try:
				socketCliente.bind((iplocal,porta))
			except:
				print "\nNão foi possível criar socket! Erro...\n"

			socketCliente.sendto(cep,(ipservidor,6666))
			resJson, dadosServer = socketCliente.recvfrom(1024)
	
			if resJson=="CEP não encontrado!":
				print resJson
			else:
				objetoJson = json.loads(resJson)
				print "CEP: ",objetoJson['cep']
				print "Rua: ",objetoJson['logradouro']
				print "Bairro: ",objetoJson['bairro']
				print "Cidade: ",objetoJson['localidade']
				print "Estado: ",objetoJson['uf']
	
			socketCliente.close()
