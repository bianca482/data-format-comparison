## Space Efficiency, Serialization Speed and Usage Complexity of Data Exchange Formats

In this repository, various data format variants are used to serialize and deserialize eight different use cases.
Overall, 27 data format variants have been implemented, namely Avro, Cap'n Proto (packed and unpacked), FlatBuffers, Thrift, TLV, BSON, CBOR, CSV (with and without header), EXI, FlexBuffers, Hessian, INI, Amazon Ion (textual and binary), Java Serialization, JSON, MessagePack, Protobuf, RDF (RDF/XML and Turtle format), Smile, TOML, UBJSON, XDR, XML and YAML.

The "Supplement" file contains additional information which could not be included in the associated paper due to the number of pages limitation. All data formats identified from the literature review are described in further detail in this document. In addition, tables presenting the absolute values measured for both serialization speed and space efficiency are shown. 

### Structure
The use cases ("ImageData", "HeartData", "ImageDescriptor", "LocationData", "HttpResponse", "Person", "SensorValue", "SmartLightController") have been defined in the "models" folder, whereas "formatwriter" contains one class per data format variant that handles the serialization/ deserialization of the respective format.

For determining the space efficiency of those formats, each serialized object is written into a file. The size of this file is then measured and written into the "results/fileSizes.xlsx" file. 
Serialization speed on the other hand refers to the whole time a format needed to transform the original object into the serialized representation. Note, that this includes all steps, even the preparatory work, e.g. allocation the buffer size or transforming the object into the format specific representation, so it can be serialized. The file "results/elapsedTimes" summarizes the measured results for each use case. 

In the folder "analysis", Jupyter Notebooks to analyse the results can be found. One notebook handles the analysis regarding space efficiency ("size_comparison.ipynb"), while another one handles the results of the measured serialization times ("speed_comparison.ipynb"). A third notebooks visualizes the usage complexity values determined in the associated paper in relation to the size and speed values measured. 

### Results

For each use case, the first image shows the three data formats that produced the smallest file sizes. The second image shows for each use case, which data formats performed the serialization the fastest.
![Three most space efficient data formats](/images/top3_size.png)

![Three data formats with fastest serialization speed](/images/top3_speed.png)

The ImageData use case has been analyzed separately because of the significant difference in space requirements and serialization speed to the other use cases. The results for this use case are visualized in the notebooks mentioned above.
