$(document).ready(() => {
   $(document).on('change', '.product-cart-price-field', function() {
       const $productCartPrice = $('.product-cart-price');
       const delta = parseFloat($(this).data('delta'));
       const quantity = parseInt($(this).val());
       const newPrice = delta * quantity;
       $productCartPrice.html(`$${newPrice.toFixed(2)}`);
       $productCartPrice.attr('data-price', newPrice);
   });
});