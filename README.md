# search-api

API de busca implementada utilizando Spark e Solr. Atualmente está sendo utilizada para buscas no meu blog. Note que ela está 
em desenvolvimento é resultado de um processo de aprendizagem, então qualquer problema certamente será corrigido no futuro.

A API Spark e a instância Solr rodam em containers distintos e o Solr está configurado para integrar com o MySQL. 
O deploy correto depende de uma instância do Vault devidamente configurada e acessada via Jenkins, além de um banco 
em uma instância MySQL.
