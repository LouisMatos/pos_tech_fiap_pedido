package br.com.postechfiap.jlapppedido.domain.pedido.mapper;

class ItemPedidoMapperTest {
  //
  // @Test
  // @DisplayName("Should map ItemPedidoSchema to ItemPedido domain")
  // void shouldMapSchemaToDomain() {
  // ItemPedidoSchema itemPedidoSchema = new ItemPedidoSchema();
  // itemPedidoSchema.setId(1L);
  // itemPedidoSchema.setPedidoid(1L);
  // itemPedidoSchema.setQuantidade(1);
  // itemPedidoSchema.setObservacao("Test");
  //
  // ItemPedido itemPedido = ItemPedidoMapper.toDomain(itemPedidoSchema);
  //
  // assertEquals(itemPedidoSchema.getId(), itemPedido.getId());
  // assertEquals(itemPedidoSchema.getPedidoid(), itemPedido.getPedidoid());
  // assertEquals(itemPedidoSchema.getQuantidade(), itemPedido.getQuantidade());
  // assertEquals(itemPedidoSchema.getObservacao(), itemPedido.getObservacao());
  // }
  //
  // @Test
  // @DisplayName("Should map ItemPedido to ItemPedidoDTO")
  // void shouldMapDomainToDTO() {
  // ItemPedido itemPedido = createFakeItemPedido();
  //
  // ItemPedidoDTO itemPedidoDTO = ItemPedidoMapper.toDTO(itemPedido);
  //
  // assertEquals(itemPedido.getId(), itemPedidoDTO.getId());
  // assertEquals(itemPedido.getPedidoid(), itemPedidoDTO.getPedidoid());
  // assertEquals(itemPedido.getQuantidade(), itemPedidoDTO.getQuantidade());
  // assertEquals(itemPedido.getObservacao(), itemPedidoDTO.getObservacao());
  // }
  //
  // @Test
  // @DisplayName("Should map ItemPedidoDTO to ItemPedido domain")
  // void shouldMapDTOToDomain() {
  // ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
  // itemPedidoDTO.setId(1L);
  // itemPedidoDTO.setPedidoid(1L);
  // itemPedidoDTO.setQuantidade(1);
  // itemPedidoDTO.setObservacao("Test");
  //
  // ProdutoDTO produtoDTO = new ProdutoDTO();
  // produtoDTO.setNome("Test");
  //
  // produtoDTO.setPreco(new BigDecimal("1.0"));
  // produtoDTO.setDescricao("Test");
  // CategoriaDTO categoriaDTO = new CategoriaDTO();
  // categoriaDTO.setId(1L);
  // categoriaDTO.setNome("Test");
  // produtoDTO.setCategoriaId(categoriaDTO.getId());
  // produtoDTO.setCategoriaNome(categoriaDTO.getNome());
  //
  //
  // ItemPedido itemPedido = ItemPedidoMapper.toDomain(itemPedidoDTO);
  //
  // assertEquals(itemPedidoDTO.getId(), itemPedido.getId());
  // assertEquals(itemPedidoDTO.getPedidoid(), itemPedido.getPedidoid());
  // assertEquals(itemPedidoDTO.getQuantidade(), itemPedido.getQuantidade());
  // assertEquals(itemPedidoDTO.getObservacao(), itemPedido.getObservacao());
  // }
  //
  // @Test
  // @DisplayName("Should map List of ItemPedidoSchema to List of ItemPedido domain")
  // void shouldMapListSchemaToDomain() {
  // ItemPedidoSchema itemPedidoSchema = new ItemPedidoSchema();
  // itemPedidoSchema.setId(1L);
  // itemPedidoSchema.setPedidoid(1L);
  // itemPedidoSchema.setQuantidade(1);
  // itemPedidoSchema.setObservacao("Test");
  //
  // List<ItemPedido> itemPedidoList =
  // ItemPedidoMapper.toListDomain(Collections.singletonList(itemPedidoSchema));
  //
  // assertNotNull(itemPedidoList);
  // assertEquals(1, itemPedidoList.size());
  // assertEquals(itemPedidoSchema.getId(), itemPedidoList.get(0).getId());
  // assertEquals(itemPedidoSchema.getPedidoid(), itemPedidoList.get(0).getPedidoid());
  // assertEquals(itemPedidoSchema.getQuantidade(), itemPedidoList.get(0).getQuantidade());
  // assertEquals(itemPedidoSchema.getObservacao(), itemPedidoList.get(0).getObservacao());
  // }
  //
  // @Test
  // @DisplayName("Should map List of ItemPedido to List of ItemPedidoDTO")
  // void shouldMapListDomainToDTO() {
  // ItemPedido itemPedido = new ItemPedido();
  // itemPedido.setId(1L);
  // itemPedido.setPedidoid(1L);
  // itemPedido.setQuantidade(1);
  // itemPedido.setObservacao("Test");
  //
  // s
  //
  // List<ItemPedidoDTO> itemPedidoDTOList =
  // ItemPedidoMapper.toListItemPedidoDTO(Collections.singletonList(itemPedido));
  //
  // assertNotNull(itemPedidoDTOList);
  // assertEquals(1, itemPedidoDTOList.size());
  // assertEquals(itemPedido.getId(), itemPedidoDTOList.get(0).getId());
  // assertEquals(itemPedido.getPedidoid(), itemPedidoDTOList.get(0).getPedidoid());
  // assertEquals(itemPedido.getQuantidade(), itemPedidoDTOList.get(0).getQuantidade());
  // assertEquals(itemPedido.getObservacao(), itemPedidoDTOList.get(0).getObservacao());
  // }
  //
  //
  // public ItemPedido createFakeItemPedido() {
  // ItemPedido itemPedido = new ItemPedido();
  // itemPedido.setId(1L);
  // itemPedido.setPedidoid(1L);
  // itemPedido.setQuantidade(1);
  // itemPedido.setObservacao("Test");
  //
  // Produto produto = new Produto();
  // produto.setId(1L);
  // produto.setNome("Test");
  // produto.setPreco(new BigDecimal("1.0"));
  // produto.setDescricao("Test");
  // Categoria categoria = new Categoria();
  // categoria.setId(1L);
  // categoria.setNome("Test");
  // produto.setCategoria(categoria);
  // produto.setImagens(Arrays.asList("Test"));
  // itemPedido.setProduto(produto);
  //
  // return itemPedido;
  // }
  //


}
