echo "Extracting setup from excel file Slack_FAQ.xlsx"
java -cp faqsetup.jar com.capco.slack.faq.setup.output.TextFileSetupExporter "Slack_FAQ.xlsx" "../book/apache-solr/solr-qa/conf/synonyms.txt" "../book/apache-solr/solr-qa/conf/stopwords.txt" "../book/bin/faq.txt"

cd ../book/bin/


echo "Index new faq questions..."
sh indexWikipedia.sh --wikiFile faq.txt
echo "Finished executing script.. "