package scenario.xml;

import crossover.ICrossover;
import mutation.IMutation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import scenario.base.RequiredMembersResult;
import scenario.base.TSPScenario;
import scenario.variants.CrossoverVariant;
import scenario.variants.MutationVariant;
import scenario.variants.SelectionVariant;
import selection.ISelection;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLScenarioCreator {

    /**
     * Parses the XML document specified by the {@code xmlFilename} containing
     * information about different execution scenarios for the genetic algorithm.
     * Only very specific fields are recognized and can be added to the scenario
     * object:
     * {@code <selection>}          The selection algorithm
     * {@code <crossover>}          The crossover algorithm
     * {@code <mutation>}           The mutation algorithm
     * {@code <crossoverRatio>}     The probability of doing a crossover
     * {@code <mutationRatio>}      The probability of doing a mutation
     * {@code <numberOfIterations>} The maximum number of iterations
     *
     * @param xmlFileName The filename of the XML document that should be parsed
     * @return a list of all scenario instances
     * @throws XMLParseException     if the XML contains invalid attributes or tags
     * @throws IllegalStateException if a scenario has unsatisfied requirements
     *                               (missing required fields or attributes)
     */
    public List<TSPScenario> parseScenarios(String xmlFileName) throws XMLParseException, IllegalStateException {
        List<TSPScenario> scenarioList = new ArrayList<>(25);
        File definitionFile = new File(xmlFileName);

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(definitionFile);
            document.getDocumentElement().normalize();

            NodeList scenarioNodes = document.getElementsByTagName(XMLScenarioTag.PARENT_SCENARIO_TAG);

            NodeListIterator scenarioIterator = new NodeListIterator(scenarioNodes);
            while (scenarioIterator.hasNext()) {
                Node scenario = scenarioIterator.next();
                if (scenario.getNodeType() == Node.ELEMENT_NODE) {
                    scenarioList.add(buildScenario(scenario));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException exc) {
            exc.printStackTrace();
            return null;
        }
        return scenarioList;
    }

    /**
     * Builds a scenario instance out of a root scenario node. The attributes and child tags
     * are extracted to receive all information about the scenario and construct an instance
     * out of it.
     *
     * @param scenario the node of the scenario
     * @return a scenario instance
     * @throws XMLParseException     if the scenario node contains invalid child tags or attributes
     * @throws IllegalStateException if requirements are not satisfied (missing required tags or attributes)
     */
    private TSPScenario buildScenario(Node scenario) throws XMLParseException, IllegalStateException {
        Map<XMLScenarioAttribute, String> scenarioAttributes = extractScenarioAttributes(scenario);
        Map<XMLScenarioTag, String> scenarioTags = extractScenarioTags(scenario);

        RequiredMembersResult result = checkRequiredMembersSet(scenarioAttributes, scenarioTags);
        if (result.isSuccessful()) {
            return buildScenarioFromMembers(scenarioAttributes, scenarioTags);
        } else {
            throw new IllegalStateException(result.getFailMessage());
        }
    }

    /**
     * Builds a concrete scenario instance by parsing the node's contents (both attributes and
     * child nodes). The diverse algorithms are parsed by constructing concrete instances of
     * the corresponding classes.
     *
     * @param xmlAttributes the attributes attached to the scenario node
     * @param xmlTags       the xml tags which are childs of the scenario node
     * @return the constructed scenario
     */
    private TSPScenario buildScenarioFromMembers(Map<XMLScenarioAttribute, String> xmlAttributes,
                                                 Map<XMLScenarioTag, String> xmlTags) {
        TSPScenario scenario = null;
        try {
            int scenarioId = Integer.parseInt(xmlAttributes.get(XMLScenarioAttribute.id));
            ISelection selectionVariant = SelectionVariant.getInstance(xmlTags.get(XMLScenarioTag.selection));
            ICrossover crossoverVariant = CrossoverVariant.getInstance(xmlTags.get(XMLScenarioTag.crossover));
            IMutation mutationVariant = MutationVariant.getInstance(xmlTags.get(XMLScenarioTag.mutation));
            double crossoverRatio = Double.parseDouble(xmlTags.get(XMLScenarioTag.crossoverRatio));
            double mutationRatio = Double.parseDouble(xmlTags.get(XMLScenarioTag.mutationRatio));
            int numberOfIterations = Integer.parseInt(xmlTags.get(XMLScenarioTag.numberOfIterations));

            scenario = new TSPScenario(scenarioId, selectionVariant, crossoverVariant, mutationVariant,
                    crossoverRatio, mutationRatio, numberOfIterations);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        return scenario;
    }

    /**
     * Constructs a map holding all child tags of {@code scenario} and their inner text
     * representation. Throws an {@code XMLParseException} if a child cannot be recognized.
     *
     * @param scenario the node holding the child tags
     * @return a map mapping child tag identifiers to their inner text representation
     * @throws XMLParseException if an invalid tag was detected
     */
    private Map<XMLScenarioTag, String> extractScenarioTags(Node scenario) throws XMLParseException {
        Map<XMLScenarioTag, String> xmlTags = new HashMap<>(scenario.getChildNodes().getLength());
        NodeListIterator childIterator = new NodeListIterator(scenario.getChildNodes());
        while (childIterator.hasNext()) {
            Node scenarioMember = childIterator.next();
            if (scenarioMember.getNodeType() == Node.ELEMENT_NODE) {
                if (XMLScenarioTag.contains(scenarioMember.getNodeName())) {
                    xmlTags.put(XMLScenarioTag.valueOf(scenarioMember.getNodeName()),
                            scenarioMember.getTextContent());
                } else {
                    throw new XMLParseException("invalid scenario tag <" +
                            scenarioMember.getNodeName() + "> detected");
                }

            }
        }

        return xmlTags;
    }

    /**
     * Constructs a map holding all attributes and their values attached to the
     * {@code scenario} node.
     *
     * @param scenario the node holding the attributes
     * @return a map mapping attribute's identifiers to their values
     * @throws XMLParseException if an invalid attribute was detected
     */
    private Map<XMLScenarioAttribute, String> extractScenarioAttributes(Node scenario) throws XMLParseException {
        Map<XMLScenarioAttribute, String> xmlAttributes = new HashMap<>(scenario.getAttributes().getLength());
        for (Node node : NamedNodeMapIterable.of(scenario.getAttributes())) {
            if (XMLScenarioAttribute.contains(node.getNodeName())) {
                xmlAttributes.put(XMLScenarioAttribute.valueOf(node.getNodeName()), node.getTextContent());
            } else {
                throw new XMLParseException(String.format("invalid attribute '%s' found on tag <%s>",
                        node.getNodeName(), scenario.getNodeName()));
            }
        }

        return xmlAttributes;
    }

    /**
     * Checks all found attributes and tags for correctness and requirement. If a required tag
     * was not found, an error object in form of a {@code RequiredMembersResult} is returned.
     *
     * @param xmlAttributes all scenario attributes
     * @param xmlTags       all scenario child tags
     * @return a result object holding the verification result and an optional failure message if
     * there are unsatisfied requirements.
     */
    private RequiredMembersResult checkRequiredMembersSet(Map<XMLScenarioAttribute, String> xmlAttributes,
                                                          Map<XMLScenarioTag, String> xmlTags) {
        RequiredMembersResult attributeResult = verifyRequiredEnumFields(XMLScenarioAttribute.class,
                xmlAttributes, "required <scenario> attribute '%s' is missing");
        RequiredMembersResult tagResult = verifyRequiredEnumFields(XMLScenarioTag.class,
                xmlTags, "required <scenario> tag <%s> is missing");
        return attributeResult.isSuccessful() ? tagResult : attributeResult;
    }

    /**
     * Verifies if the map {@code members} contains all constants defined in the enumeration of type
     * {@code enumType} as keys which are supposed to be required. Any mismatch between elements of
     * enum {@code enumType} is considered as unsatisfied requirement.
     *
     * @param enumType      the class object representing an enumeration type
     * @param members       the members of a tag having {@code enumType} as key
     * @param formatMessage a message that can be written in the output result object in case
     *                      a requirement is unsatisfied. A placeholder '%s' can be used to
     *                      be replaced with the mismatched member name.
     * @param <E>           the type of the enumeration's contents
     * @return a result object holding the verification result and an optional failure message if
     * there are unsatisfied requirements.
     */
    private <E extends Enum<E>> RequiredMembersResult verifyRequiredEnumFields(Class<E> enumType, Map<E, String> members,
                                                                               String formatMessage) {
        RequiredMembersResult result = new RequiredMembersResult();
        for (E identifier : enumType.getEnumConstants()) {
            if (!members.containsKey(identifier)) {
                result.fails();
                result.setFailMessage(String.format(formatMessage, identifier.name()));
            }
        }

        return result;
    }
}
