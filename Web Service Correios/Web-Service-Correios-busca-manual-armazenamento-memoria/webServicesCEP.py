#coding: utf-8
import urllib
import urllib2
import re
import socket
import exceptions
import json

url = 'http://m.correios.com.br/movel/buscaCepConfirma.do'
iplocal = 'localhost'
porta = 6666
cache = {'ceps':[]}
cache = json.dumps(cache)
cache = json.loads(cache)

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
	
	i=0
	achou = False
	while i<len(cache['ceps']):
		if cache['ceps'][i]['CEP']==cep:
			achou = True	
			break
		i+=1

	if achou==True:
		print '\tCEP está armazenado em buffer! Retornando resultado...'
		respJson = cache['ceps'][i]
		respJson = json.dumps(respJson)
	else:
		print '\tBuscando CEP na base de dados dos correios...'
		p = {'cepEntrada': cep, 'tipoCep': '', 'cepTemp': '', 'metodo': 'buscarCep'}
		post = urllib.urlencode(p)
		req = urllib2.Request(url, post)
		con = urllib2.urlopen(req)
		html = con.read().decode('iso-8859-1')

		html2 = html.replace('\n', '').replace('\t', '').replace('  ', '')	
		resultado = re.findall(r'<span class="respostadestaque">(.*?)</span>',html2)
	
		print '\tRetornando resultado...'
		if len(resultado)==0:
			respJson = 'CEP não encontrado!'
		else:
			if len(resultado)==2:
				resp = {'CEP':resultado[1],'Rua':'','Bairro':'','Cidade/Estado':resultado[0]}
			else:
				resp = {'CEP':resultado[3],'Rua':resultado[0],'Bairro':resultado[1],'Cidade/Estado':resultado[2]}

			resp = json.dumps(resp)
			respJson = json.loads(resp)
			cache['ceps'].append(respJson)
			respJson = json.dumps(respJson) # deixa string pura para ser passada por socket
	
		print 'CEPS em cache: '
		print cache['ceps']

	socketServer.sendto(respJson,(dadosCliente[0],dadosCliente[1]))
	socketServer.close()


