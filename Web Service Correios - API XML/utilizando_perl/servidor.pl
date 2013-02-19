#!/usr/bin/perl
#server.pl

use IO::Socket::INET;
require LWP::UserAgent;
require HTTP::Request;

my ($socket,$cep);

$socket = new IO::Socket::INET (
	LocalPort => '5000',
	Proto => 'udp',
) or die "Erro na criação do socket : $!\n";

my ($requisicao,$agentHTTP);

$requisicao = HTTP::Request->new();
$agentHTTP = LWP::UserAgent->new;

while(1)
{
	# esperando requisição do cliente para a leitura, através do socket
	$socket->recv($cep,1024);

	#recupera os dados de porta e host dos dados recebidos, além da mensagem
	$peer_address = $socket->peerhost();
	$peer_port = $socket->peerport();
	print "\n($peer_address , $peer_port) CEP : $cep";
	
	#trabalha a requisicao
	$requisicao->method("GET");
	$requisicao->uri("http://cep.republicavirtual.com.br/web_cep.php?cep=$cep");
	$response = $agentHTTP->request($requisicao);

	#responde ao cliente
	$resultado = "$response->{_content}";
	print "\nRespondido ao Cliente! \n";
	$socket->send($resultado);
	print $socket "$data";

}

$socket->close();
